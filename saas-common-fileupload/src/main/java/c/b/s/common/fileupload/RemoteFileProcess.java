package c.b.s.common.fileupload;

import c.b.s.common.dal.datasource.RoutingDataSourceHolder;
import c.b.s.common.logging.domain.ImportRecord;
import c.b.s.common.logging.domain.ImportRecordRepository;
import c.b.s.common.message.domain.Message;
import c.b.s.common.message.domain.MessageRepository;
import c.b.s.common.util.DateUtil;
import c.b.s.common.util.ExecutorServiceUtil;
import c.b.s.common.util.RequestUtil;
import c.b.s.common.util.UserInfoUtil;
import c.b.s.common.util.constant.BoxinConstant;
import c.b.s.common.util.enums.ImportTypeEnum;
import c.b.s.common.util.enums.MessageTypeEnum;
import c.b.s.common.util.excel.XlsxProcessWrite;
import c.b.s.common.util.lineup.OneTaskPerMachine;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by guiqingqing on 2018/8/30.
 */
public abstract class RemoteFileProcess {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteFileProcess.class);
    protected final ThreadLocal<Map<String, Object>> PARAMS_THREAD_LOCAL = new ThreadLocal();

    @Autowired
    private FileService fileService;
    @Autowired
    private ImportRecordRepository importRecordRepository;
    @Autowired
    private MessageRepository messageRepository;

    /**
     * 处理上传文件
     *   约定: 仅适用于上传Excel文件, 且title只能占用第一行
     * @param fileName 文件名
     * @param importType 导入类型, 如批量新增组织、批量新增用户
     * @param adminId 操作人ID
     * @param adminRealName 操作人姓名
     * @param applicationId 应用ID
     * @return
     */
    public boolean upload(String fileName, ImportTypeEnum importType, long adminId, String adminRealName, long applicationId) {
        return upload(fileName, importType, adminId, adminRealName, applicationId, new HashMap());
    }

    /**
     * 处理上传文件
     *   约定: 仅适用于上传Excel文件, 且title只能占用第一行
     * @param fileName 文件名
     * @param importType 导入类型, 如批量新增组织、批量新增用户
     * @param adminId 操作人ID
     * @param adminRealName 操作人姓名
     * @param applicationId 应用ID
     * @param params 额外参数
     * @return
     */
    public boolean upload(String fileName, ImportTypeEnum importType, long adminId, String adminRealName, long applicationId, Map<String, Object> params) {
        if (Objects.nonNull(params) && params.containsKey("platform")) {
            return uploadByPlatform(fileName, importType, adminId, adminRealName, applicationId, params, false);
        } else {
            return upload(fileName, importType, adminId, adminRealName, applicationId, params, false);
        }
    }

    public boolean parallelUpload(String fileName, ImportTypeEnum importType, long adminId, String adminRealName, long applicationId) {
        return parallelUpload(fileName, importType, adminId, adminRealName, applicationId, new HashMap());
    }

    public boolean parallelUpload(String fileName, ImportTypeEnum importType, long adminId, String adminRealName, long applicationId, Map<String, Object> params) {
        return upload(fileName, importType, adminId, adminRealName, applicationId, params, true);
    }

    private boolean uploadByPlatform(String fileName, ImportTypeEnum importType, long adminId, String adminRealName, long applicationId, Map<String, Object> params, boolean isParallel) {
        try {
            Long userId = UserInfoUtil.getUserId();
            String userName = UserInfoUtil.getUserName();
            String realName = UserInfoUtil.getRealName();
            Long appId = UserInfoUtil.getApplicationId();
            Long tenantId = UserInfoUtil.getTenantId();
            String token = UserInfoUtil.getToken();
            Map<String, String> headers = RequestUtil.getHeaders();

            // 处理文件
            ExecutorServiceUtil.fixedExecutorService.submit(() -> {
                try(OneTaskPerMachine oneTaskPerMachine = new OneTaskPerMachine()) {
                    oneTaskPerMachine.acquire();
                    Map<Integer, List<String>> dataMap = fileService.download(fileName);
                    int totalCount = dataMap.size() - 1; // 去掉标题占用的那一行
                    totalCount = totalCount > 0 ? totalCount : 0;
                    // 记录上传日志
                    Long assetId = null;
                    if(Objects.nonNull(params.get("assetId"))){
                        assetId = (Long) params.get("assetId");
                    }
                    ImportRecord importRecord = ImportRecord.builder()
                            .taskId(generateTaskId(assetId, tenantId))
                            .assetId(assetId)
                            .fileName(fileName)
                            .importType(ImportTypeEnum.CASE_IMPORT.getType())
                            .originalFileUrl(fileName)
                            .totalCount(totalCount)
                            .adminId(userId)
                            .adminRealName(realName)
                            .build();
                    importRecordRepository.save(importRecord);

                    UserInfoUtil.setUserId(userId);
                    UserInfoUtil.setUserName(userName);
                    UserInfoUtil.setRealName(realName);
                    UserInfoUtil.setApplicationId(appId);
                    UserInfoUtil.setTenantId(tenantId);
                    UserInfoUtil.setToken(token);
                    RequestUtil.setHeaders(headers);
                    PARAMS_THREAD_LOCAL.set(params);
                    if (isParallel) {
                        parallelHandleExcel(dataMap, fileName, importType, adminId, adminRealName, applicationId, importRecord.getId());
                    } else {
                        handleExcel(dataMap, fileName, importType, adminId, adminRealName, applicationId, importRecord.getId());
                    }
                    UserInfoUtil.clear();
                    RequestUtil.clearHeader();
                    PARAMS_THREAD_LOCAL.remove();
                } catch (Exception e) {
                    LOGGER.error("处理文件异常, fileName: {}", fileName, e);
                } finally {
                    callback(userId, tenantId);
                }
            });
            return true;
        } catch (Exception e) {
            LOGGER.error("处理上传文件异常, 文件名: {}, 上传类型: {}, 操作人: {}({})", fileName, importType.getDescription(), adminRealName, adminId, e);
            return false;
        }
    }

    private boolean upload(String fileName, ImportTypeEnum importType, long adminId, String adminRealName, long applicationId, Map<String, Object> params, boolean isParallel) {
        try {
            Long userId = UserInfoUtil.getUserId();
            String userName = UserInfoUtil.getUserName();
            String realName = UserInfoUtil.getRealName();
            Long appId = UserInfoUtil.getApplicationId();
            Long tenantId = UserInfoUtil.getTenantId();
            String token = UserInfoUtil.getToken();
            Map<String, String> headers = RequestUtil.getHeaders();

            Object dataSourceKey = RoutingDataSourceHolder.getCurrentDataSourceKey();
            // 处理文件
            ExecutorServiceUtil.fixedExecutorService.submit(() -> {
                try(OneTaskPerMachine oneTaskPerMachine = new OneTaskPerMachine()) {
                    oneTaskPerMachine.acquire();
                    UserInfoUtil.setUserId(userId);
                    UserInfoUtil.setUserName(userName);
                    UserInfoUtil.setRealName(realName);
                    UserInfoUtil.setApplicationId(appId);
                    UserInfoUtil.setTenantId(tenantId);
                    UserInfoUtil.setToken(token);
                    RequestUtil.setHeaders(headers);
                    RoutingDataSourceHolder.setDataSourceKey(dataSourceKey);
                    PARAMS_THREAD_LOCAL.set(params);

                    Map<Integer, List<String>> dataMap = fileService.download(fileName);
                    int totalCount = dataMap.size() - 1; // 去掉标题占用的那一行
                    totalCount = totalCount > 0 ? totalCount : 0;
                    // 记录上传日志
                    Long assetId = null;
                    if(Objects.nonNull(params.get("assetId"))){
                        assetId = (Long) params.get("assetId");
                    }
                    ImportRecord importRecord = ImportRecord.builder()
                            .taskId(generateTaskId(assetId, tenantId))
                            .assetId(assetId)
                            .fileName(fileName)
                            .importType(importType.getType())
                            .originalFileUrl(fileName)
                            .totalCount(totalCount)
                            .adminId(adminId)
                            .adminRealName(adminRealName)
                            .build();
                    importRecordRepository.save(importRecord);

                    if (isParallel) {
                        parallelHandleExcel(dataMap, fileName, importType, adminId, adminRealName, applicationId, importRecord.getId());
                    } else {
                        handleExcel(dataMap, fileName, importType, adminId, adminRealName, applicationId, importRecord.getId());
                    }
                    UserInfoUtil.clear();
                    RequestUtil.clearHeader();
                    RoutingDataSourceHolder.removeDataSourceKey(dataSourceKey);
                    PARAMS_THREAD_LOCAL.remove();
                } catch (Exception e) {
                    LOGGER.error("处理文件异常, fileName: {}", fileName, e);
                } finally {
                    callback(userId, tenantId);
                }
            });
            return true;
        } catch (Exception e) {
            LOGGER.error("处理上传文件异常, 文件名: {}, 上传类型: {}, 操作人: {}({})", fileName, importType.getDescription(), adminRealName, adminId, e);
            return false;
        }
    }

    public abstract void callback(Long adminId, Long tenantId);

    private void handleExcel(Map<Integer, List<String>> dataMap, String fileName, ImportTypeEnum importType, long adminId, String adminRealName, long applicationId, long importId) {
        int success = 0; // 记录成功条数
        int total; // 总条数
        String now = DateUtil.now();
        try {
            Map<Integer, String> header = getHeader(dataMap.get(0));
            dataMap.remove(0);
            total = dataMap.size(); // 去掉title后总条数
            Iterator<Map.Entry<Integer, List<String>>> iterator = dataMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, List<String>> entry = iterator.next();
                if (entry.getKey() == 0) {
                    // title
                } else {
                    // content
                    success += process(entry.getValue(), adminId, adminRealName);
                }
            }
            SXSSFWorkbook workbook = new XlsxProcessWrite().generateWorkbook(header, dataMap);

            int idx = fileName.lastIndexOf(".");
            if (idx > 0) {
                fileName = fileName.substring(0, idx) + "-result" + fileName.substring(idx);
            } else {
                fileName = fileName + "-result";
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            // 上传处理后的文件
            fileService.upload(fileName, new ByteArrayInputStream(baos.toByteArray()));
            // 更新导入记录
            ImportRecord importRecord = ImportRecord.builder()
                    .id(importId)
                    .handledFileUrl(fileName)
                    .status(BoxinConstant.IMPORT_RECORD_STATUS_HANDLED)
                    .successCount(success)
                    .build();
            importRecordRepository.modify(importRecord);
            baos.close();
            workbook.close();

            String subject = String.format(BoxinConstant.UPLOAD_FILE_MESSAGE_TITLE_TEMPLATE, MessageTypeEnum.BATCH_UPLOAD.getDescription(), importType.getDescription());
            String content = String.format(BoxinConstant.UPLOAD_FILE_NEW_MESSAGE_SUCCESS_TEMPLATE, now, importType.getDescription(), total, success, total - success);

            writeMessage(adminId, applicationId, MessageTypeEnum.BATCH_UPLOAD, subject, content, fileName);
        } catch (Exception e) {
            LOGGER.error("批处理异常, fileName: {}, importId: {}", fileName, importId, e);
            writeMessage(adminId, applicationId, MessageTypeEnum.BATCH_UPLOAD,
                    String.format(BoxinConstant.UPLOAD_FILE_MESSAGE_TITLE_TEMPLATE, MessageTypeEnum.BATCH_UPLOAD.getDescription(), importType.getDescription()),
                    String.format(BoxinConstant.UPLOAD_FILE_MESSAGE_FAIL_TEMPLATE, now, importType.getDescription()), fileName);
        }
    }

    private void parallelHandleExcel(Map<Integer, List<String>> dataMap, String fileName, ImportTypeEnum importType, long adminId, String adminRealName, long applicationId, long importId) {
        int success = 0; // 记录成功条数
        int total; // 总条数
        String now = DateUtil.now();
        List<ListenableFuture<Integer>> futures = new ArrayList();
        try {
            Map<Integer, String> header = getHeader(dataMap.get(0));
            dataMap.remove(0);
            total = dataMap.size(); // 去掉title后总条数
            Iterator<Map.Entry<Integer, List<String>>> iterator = dataMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, List<String>> entry = iterator.next();
                if (entry.getKey() == 0) {
                    // title
                } else {
                    // content
                    Long userId = UserInfoUtil.getUserId();
                    String userName = UserInfoUtil.getUserName();
                    String realName = UserInfoUtil.getRealName();
                    Long appId = UserInfoUtil.getApplicationId();
                    Long tenantId = UserInfoUtil.getTenantId();
                    String token = UserInfoUtil.getToken();
                    Map<String, String> headers = RequestUtil.getHeaders();

                    Object dataSourceKey = RoutingDataSourceHolder.getCurrentDataSourceKey();
                    Map<String, Object> params = PARAMS_THREAD_LOCAL.get();
                    futures.add(ExecutorServiceUtil.fixedExecutorService.submit(() -> {
                        UserInfoUtil.setUserId(userId);
                        UserInfoUtil.setUserName(userName);
                        UserInfoUtil.setRealName(realName);
                        UserInfoUtil.setApplicationId(appId);
                        UserInfoUtil.setTenantId(tenantId);
                        UserInfoUtil.setToken(token);
                        RequestUtil.setHeaders(headers);
                        RoutingDataSourceHolder.setDataSourceKey(dataSourceKey);
                        PARAMS_THREAD_LOCAL.set(params);
                        int count = process(entry.getValue(), adminId, adminRealName);
                        UserInfoUtil.clear();
                        RequestUtil.clearHeader();
                        RoutingDataSourceHolder.removeDataSourceKey(dataSourceKey);
                        PARAMS_THREAD_LOCAL.remove();
                        return count;
                    }));
                }
            }
            ListenableFuture<List<Integer>> resultsFuture = Futures.allAsList(futures);
            for (int count : resultsFuture.get()) {
                success += count;
            }
            SXSSFWorkbook workbook = new XlsxProcessWrite().generateWorkbook(header, dataMap);

            int idx = fileName.lastIndexOf(".");
            if (idx > 0) {
                fileName = fileName.substring(0, idx) + "-result" + fileName.substring(idx);
            } else {
                fileName = fileName + "-result";
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            // 上传处理后的文件
            fileService.upload(fileName, new ByteArrayInputStream(baos.toByteArray()));
            // 更新导入记录
            ImportRecord importRecord = ImportRecord.builder()
                    .id(importId)
                    .handledFileUrl(fileName)
                    .status(BoxinConstant.IMPORT_RECORD_STATUS_HANDLED)
                    .successCount(success)
                    .build();
            importRecordRepository.modify(importRecord);
            baos.close();
            workbook.close();

            String subject = String.format(BoxinConstant.UPLOAD_FILE_MESSAGE_TITLE_TEMPLATE, MessageTypeEnum.BATCH_UPLOAD.getDescription(), importType.getDescription());
            String content = String.format(BoxinConstant.UPLOAD_FILE_NEW_MESSAGE_SUCCESS_TEMPLATE, now, importType.getDescription(), total, success, total - success);

            writeMessage(adminId, applicationId, MessageTypeEnum.BATCH_UPLOAD, subject, content, fileName);
        } catch (Exception e) {
            LOGGER.error("批处理异常, fileName: {}, importId: {}", fileName, importId, e);
            writeMessage(adminId, applicationId, MessageTypeEnum.BATCH_UPLOAD,
                    String.format(BoxinConstant.UPLOAD_FILE_MESSAGE_TITLE_TEMPLATE, MessageTypeEnum.BATCH_UPLOAD.getDescription(), importType.getDescription()),
                    String.format(BoxinConstant.UPLOAD_FILE_MESSAGE_FAIL_TEMPLATE, now, importType.getDescription()), fileName);
        }
    }

    /**
     * 具体处理Excel的逻辑，交由子类实现
     * @param row
     * @param adminId
     * @param adminRealName
     * @return
     */
    public abstract int process(List<String> row, long adminId, String adminRealName);

    /**
     * 添加一条Message信息
     * @param adminId
     * @param applicationId
     * @param type
     * @param subject
     * @param content
     */
    private void writeMessage(long adminId, long applicationId, MessageTypeEnum type, String subject, String content, String fileName) {
        Message message = Message.builder()
                .userId(adminId)
                .applicationId(applicationId)
                .type(type.getType())
                .subject(subject)
                .content(content)
                .fileName(fileName)
                .build();
        messageRepository.save(message);
    }

    private Map<Integer, String> getHeader(List<String> header) {
        Map<Integer, String> headMap = new HashMap();
        for (int i = 0; i < header.size(); i++) {
            headMap.put(i, header.get(i));
        }
        headMap.put(header.size(), "处理结果");
        return headMap;
    }

    private String generateTaskId(Long assetId, Long tenantId) {
        LOGGER.info("RemoteFileProcess - 资方ID: {} 商户ID: {}", assetId, tenantId);
        if(Objects.nonNull(assetId)){
            return tenantId + "_" + assetId + "_" + DateUtil.fmtLocalDateTimeToStr(LocalDateTime.now(), "yyyyMMddHHmmss");
        }
        return tenantId + "_xxx_" + DateUtil.fmtLocalDateTimeToStr(LocalDateTime.now(), "yyyyMMddHHmmss");
    }
}