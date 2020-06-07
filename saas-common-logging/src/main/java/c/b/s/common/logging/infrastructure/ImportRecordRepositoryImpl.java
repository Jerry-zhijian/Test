package c.b.s.common.logging.infrastructure;

import c.b.s.common.logging.domain.ImportRecord;
import c.b.s.common.logging.domain.ImportRecordRepository;
import c.b.s.common.logging.infrastructure.jooq.tables.records.TbImportRecordRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.UInteger;
import org.jooq.types.ULong;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static c.b.s.common.logging.infrastructure.jooq.Tables.TB_IMPORT_RECORD;
import static c.b.s.common.logging.infrastructure.jooq.tables.TbImportRecord.*;

/**
 * Created by guiqingqing on 2018/8/29.
 */
public class ImportRecordRepositoryImpl implements ImportRecordRepository {
    @Autowired
    private DSLContext dsl;

    @Override
    public ImportRecord findImportRecordById(Long id) {
        TbImportRecordRecord record = dsl.selectFrom(TB_IMPORT_RECORD)
                .where(ISACTIVE.eq(Byte.valueOf("1")))
                .and(ID.eq(ULong.valueOf(id))).limit(1).fetchOne();

        return record == null ? null : toEntity(record);
    }

    @Override
    public int save(ImportRecord importRecord) {
        InsertSetMoreStep insertSetMoreStep = dsl.insertInto(TB_IMPORT_RECORD)
                .set(TASK_ID, importRecord.getTaskId())
                .set(FILE_NAME, importRecord.getFileName())
                .set(IMPORT_TYPE, UByte.valueOf(importRecord.getImportType()))
                .set(ORIGINAL_FILE_URL, importRecord.getOriginalFileUrl())
                .set(TOTAL_COUNT, UInteger.valueOf(importRecord.getTotalCount()))
                .set(ADMIN_ID, ULong.valueOf(importRecord.getAdminId()))
                .set(ADMIN_REAL_NAME, importRecord.getAdminRealName());
        if (Objects.nonNull(importRecord.getAssetId())) {
            insertSetMoreStep.set(ASSET_ID, ULong.valueOf(importRecord.getAssetId()));
        }
        if (insertSetMoreStep.isExecutable()) {
            Record record = insertSetMoreStep.returning().fetchOne();
            importRecord.setId(((TbImportRecordRecord) record).getId().longValue());
            return 1;
        } else {
            return 0;
        }

    }

    @Override
    public int modify(ImportRecord importRecord) {
        UpdateQuery delegate = dsl.updateQuery(TB_IMPORT_RECORD);
        if (Objects.nonNull(importRecord.getHandledFileUrl())) {
            delegate.addValue(HANDLED_FILE_URL, importRecord.getHandledFileUrl());
        }
        if (Objects.nonNull(importRecord.getStatus())) {
            delegate.addValue(STATUS, importRecord.getStatus());
        }
        if (Objects.nonNull(importRecord.getSuccessCount())) {
            delegate.addValue(SUCCESS_COUNT, importRecord.getSuccessCount());
        }
        if (Objects.nonNull(importRecord.getNewUserCount())) {
            delegate.addValue(NEW_USER_COUNT, importRecord.getNewUserCount());
        }
        if (Objects.nonNull(importRecord.getOldUserCount())) {
            delegate.addValue(OLD_USER_COUNT, importRecord.getOldUserCount());
        }
        if (Objects.nonNull(importRecord.getTotalUserCount())) {
            delegate.addValue(TOTAL_USER_COUNT, importRecord.getTotalUserCount());
        }
        delegate.addConditions(ID.eq(ULong.valueOf(importRecord.getId())));
        return delegate.isExecutable() ? delegate.execute() : 0;
    }

    @Override
    public int pagingImportRecordCount(Long assetId, String taskId, Date beginTime, Date endTime, Long userId) {
        SelectConditionStep selectConditionStep = dsl.selectCount()
                .from(TB_IMPORT_RECORD)
                .where(ISACTIVE.eq(Byte.valueOf("1")));
        if (Objects.nonNull(assetId)) {
            selectConditionStep.and(ASSET_ID.eq(ULong.valueOf(assetId)));
        }
        if (Objects.nonNull(taskId)) {
            selectConditionStep.and(TASK_ID.eq(taskId));
        }
        if (Objects.nonNull(userId)) {
            selectConditionStep.and(ADMIN_ID.eq(ULong.valueOf(userId)));
        }
        if (Objects.nonNull(beginTime)) {
            selectConditionStep.and(INSERTTIME.ge(new Timestamp(beginTime.getTime())));
        }
        if (Objects.nonNull(endTime)) {
            selectConditionStep.and(INSERTTIME.le(new Timestamp(endTime.getTime())));
        }
        return ((Integer) selectConditionStep.fetch(0).get(0)).intValue();
    }

    @Override
    public List<ImportRecord> pagingImportRecord(int pageNum, int pageSize, Long assetId, String taskId, Date beginTime, Date endTime, Long userId) {
        SortField sortField = DSL.field(INSERTTIME).desc();
        SelectConditionStep selectConditionStep = dsl.selectFrom(TB_IMPORT_RECORD)
                .where(ISACTIVE.eq(Byte.valueOf("1")));
        if (Objects.nonNull(assetId)) {
            selectConditionStep.and(ASSET_ID.eq(ULong.valueOf(assetId)));
        }
        if (Objects.nonNull(taskId)) {
            selectConditionStep.and(TASK_ID.eq(taskId));
        }
        if (Objects.nonNull(userId)) {
            selectConditionStep.and(ADMIN_ID.eq(ULong.valueOf(userId)));
        }
        if (Objects.nonNull(beginTime)) {
            selectConditionStep.and(INSERTTIME.ge(new Timestamp(beginTime.getTime())));
        }
        if (Objects.nonNull(endTime)) {
            selectConditionStep.and(INSERTTIME.le(new Timestamp(endTime.getTime())));
        }
        Result<TbImportRecordRecord> records = selectConditionStep.orderBy(sortField)
                .limit(pageSize)
                .offset((pageNum - 1) * pageSize)
                .fetch();
        return records.stream()
                .map(record -> toEntity(record))
                .collect(Collectors.toList());
    }

    private ImportRecord toEntity(TbImportRecordRecord record) {
        ImportRecord importRecord =  ImportRecord.builder()
                .id(record.getId().longValue())
                .taskId(record.getTaskId())
                .fileName(record.getFileName())
                .importType(record.getImportType().byteValue())
                .originalFileUrl(record.getOriginalFileUrl())
                .handledFileUrl(record.getHandledFileUrl())
                .status(record.getStatus().byteValue())
                .totalCount(record.getTotalCount().intValue())
                .successCount(record.getSuccessCount().intValue())
                .adminId(record.getAdminId().longValue())
                .adminRealName(record.getAdminRealName())
                .insertTime(record.getInserttime())
                .build();
        if (Objects.nonNull(record.getAssetId())) {
            importRecord.setAssetId(record.getAssetId().longValue());
        }
        if (Objects.nonNull(record.getNewUserCount())) {
            importRecord.setNewUserCount(record.getNewUserCount().intValue());
        }
        if (Objects.nonNull(record.getOldUserCount())) {
            importRecord.setOldUserCount(record.getOldUserCount().intValue());
        }
        if (Objects.nonNull(record.getTotalUserCount())) {
            importRecord.setTotalUserCount(record.getTotalUserCount().intValue());
        }
        return importRecord;
    }
}