package c.b.s.common.fileupload;

import c.b.s.common.util.XLSXCovertCSVReader;
import c.b.s.common.util.excel.XlsxProcessRead;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * Created by guiqingqing on 2018/8/29.
 */
@Service
public class FileService {
    @Value("${aliyun.oss.end_point}")
    private String endPoint;
    @Value("${aliyun.oss.download_end_point}")
    private String downloadEndPoint;
    @Value("${aliyun.oss.access_key_id}")
    private String accessKeyId;
    @Value("${aliyun.oss.access_key_secret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucket_name}")
    private String bucketName;

    /**
     * 上传文件
     * @param objectName
     * @param input
     */
    public void upload(String objectName, InputStream input) {
        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, objectName, input);
        ossClient.shutdown();
    }

    /**
     * 上传直接下载的文件
     * @param objectName
     * @param input
     */
    public void uploadDownloadFile(String objectName, InputStream input) {
        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        ObjectMetadata data = new ObjectMetadata();
        data.setContentType(ContentType.APPLICATION_OCTET_STREAM.getMimeType());
        PutObjectRequest request = new PutObjectRequest(bucketName, objectName, input, data);
        ossClient.putObject(request);
        ossClient.shutdown();
    }

    /**
     * 下载文件流
     * @param objectName
     * @param output
     * @throws IOException
     */
    public void download(String objectName, OutputStream output) throws IOException {
        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);
        InputStream input = ossObject.getObjectContent();
        byte[] bs = new byte[1024];
        int count;
        while ((count = input.read(bs)) != -1) {
            output.write(bs, 0, count);
        }
        input.close();
        ossClient.shutdown();
    }

    /**
     * 下载Excel
     * @param objectName
     * @throws IOException
     */
    public Map<Integer, List<String>> download(String objectName) throws Exception {
        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);
        InputStream input = ossObject.getObjectContent();
        Map<Integer, List<String>> result = new XlsxProcessRead().processAllSheet(input);
        result.values().forEach(list -> {
            for (int i = 0; i < list.size(); i++) {
                if (Objects.isNull(list.get(i))) {
                    list.set(i, "");
                } else {
                    list.set(i, list.get(i).trim());
                }
            }
        });
        input.close();
        ossClient.shutdown();
        return result;
    }

    public List<String[]> downloadCaseImport(String objectName, int minColumns) throws Exception {
        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);
        InputStream input = ossObject.getObjectContent();
        List<String[]> result = XLSXCovertCSVReader.readerExcel(input, minColumns);
        input.close();
        ossClient.shutdown();
        return result;
    }

    /**
     * 生成下载链接(有效期10分钟)
     * @param objectName
     * @return
     */
    public String generatePresignedUrl(String objectName) {
        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 10);
        String urlLink = ossClient.generatePresignedUrl(bucketName, objectName, calendar.getTime()).toString();
        urlLink = urlLink.replaceAll("^http", "https").replaceAll(endPoint, downloadEndPoint);
        return urlLink;
    }

    /**
     * 判断文件是否存在
     * @param objectName
     * @return
     */
    public boolean exist(String objectName) {
        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        boolean found = ossClient.doesObjectExist(bucketName, objectName);
        ossClient.shutdown();
        return found;
    }

    /**
     * 生成带有时间戳的文件名，防止重复
     * */
    public String generateFileName(String originalFilename){
        int idx = originalFilename.lastIndexOf(".");
        String fileName;
        if (idx > 0) {
            fileName = originalFilename.substring(0, idx) + "-" + System.currentTimeMillis() + originalFilename.substring(idx);
        } else {
            fileName = originalFilename + "-" + System.currentTimeMillis();
        }
        return fileName;
    }

    /**
     * 下载email模板
     * @param objectName
     * @throws IOException
     */
    public String downloadEmailTemplate(String objectName) throws Exception {
        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);
        InputStream input = ossObject.getObjectContent();
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        input.close();
        ossClient.shutdown();
        return sb.toString();
    }
}