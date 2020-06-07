package c.b.s.common.logging.infrastructure;

import c.b.s.common.logging.domain.OperationLog;
import c.b.s.common.logging.domain.OperationLogRepository;
import c.b.s.common.logging.infrastructure.jooq.tables.records.CasOperationLogRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.types.ULong;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static c.b.s.common.logging.infrastructure.jooq.Tables.CAS_OPERATION_LOG;
import static c.b.s.common.logging.infrastructure.jooq.tables.CasOperationLog.*;

/**
 * Created by guiqingqing on 2018/8/11.
 */
public class OperationLogRepositoryImpl implements OperationLogRepository {
    @Autowired
    private DSLContext dsl;

    @Override
    public void save(OperationLog operationLog) {
        InsertSetMoreStep insertSetMoreStep = dsl.insertInto(CAS_OPERATION_LOG)
                .set(USER_ID, ULong.valueOf(operationLog.getUserId()))
                .set(USER_NAME, operationLog.getUserName())
                .set(REAL_NAME, operationLog.getRealName())
                .set(APPLICATION_ID, ULong.valueOf(operationLog.getApplicationId()))
                .set(OPERATION_CODE, operationLog.getOperationCode());
        if (Objects.nonNull(operationLog.getIp())) {
            insertSetMoreStep.set(IP, operationLog.getIp());
        }
        if (Objects.nonNull(operationLog.getUrl())) {
            insertSetMoreStep.set(URL, operationLog.getUrl());
        }
        if (Objects.nonNull(operationLog.getOperationValue())) {
            insertSetMoreStep.set(OPERATION_VALUE, operationLog.getOperationValue());
        }
        insertSetMoreStep.returning().fetchOne();
    }

    @Override
    public List<OperationLog> findByUserId(int pageNum, int pageSize, Long applicationId, Long userId) {
        return findByPage(pageNum, pageSize, applicationId, userId, null, null);
    }

    @Override
    public int countByUserId(Long applicationId, Long userId) {
        return count(applicationId, userId, null, null);
    }

    @Override
    public List<OperationLog> findByUserName(int pageNum, int pageSize, Long applicationId, String userName) {
        return findByPage(pageNum, pageSize, applicationId, null, userName, null);
    }

    @Override
    public int countByUserName(Long applicationId, String userName) {
        return count(applicationId, null, userName, null);
    }

    @Override
    public List<OperationLog> findByRealName(int pageNum, int pageSize, Long applicationId, String realName) {
        return findByPage(pageNum, pageSize, applicationId, null, null, realName);
    }

    @Override
    public int countByRealName(Long applicationId, String realName) {
        return count(applicationId, null, null, realName);
    }

    private List<OperationLog> findByPage(int pageNum, int pageSize, Long applicationId, Long userId, String userName, String realName) {
        SortField sortField = DSL.field(INSERTTIME).desc();
        SelectConditionStep selectConditionStep = dsl.selectFrom(CAS_OPERATION_LOG)
                .where(ISACTIVE.eq(Byte.valueOf("1")))
                .and(APPLICATION_ID.eq(ULong.valueOf(applicationId)));
        if (Objects.nonNull(userId)) {
            selectConditionStep.and(USER_ID.eq(ULong.valueOf(userId)));
        }
        if (Objects.nonNull(userName)) {
            selectConditionStep.and(USER_NAME.eq(userName));
        }
        if (Objects.nonNull(realName)) {
            selectConditionStep.and(REAL_NAME.eq(realName));
        }
        Result<CasOperationLogRecord> records = selectConditionStep.orderBy(sortField)
                .limit(pageSize)
                .offset((pageNum - 1) * pageSize)
                .fetch();
        return records.stream()
                .map(record -> toEntity(record))
                .collect(Collectors.toList());
    }

    private int count(Long applicationId, Long userId, String userName, String realName) {
        SelectConditionStep selectConditionStep = dsl.selectCount()
                .from(CAS_OPERATION_LOG)
                .where(ISACTIVE.eq(Byte.valueOf("1")))
                .and(APPLICATION_ID.eq(ULong.valueOf(applicationId)));
        if (Objects.nonNull(userId)) {
            selectConditionStep.and(USER_ID.eq(ULong.valueOf(userId)));
        }
        if (Objects.nonNull(userName)) {
            selectConditionStep.and(USER_NAME.eq(userName));
        }
        if (Objects.nonNull(realName)) {
            selectConditionStep.and(REAL_NAME.eq(realName));
        }
        return ((Integer) selectConditionStep.fetch(0).get(0)).intValue();
    }

    private OperationLog toEntity(CasOperationLogRecord record) {
        return OperationLog.builder()
                .id(record.getId().longValue())
                .userId(record.getUserId().longValue())
                .userName(record.getUserName())
                .realName(record.getRealName())
                .applicationId(record.getApplicationId().longValue())
                .ip(record.getIp())
                .url(record.getUrl())
                .operationCode(record.getOperationCode())
                .operationValue(record.getOperationValue())
                .insertTime(record.getInserttime())
                .build();
    }
}