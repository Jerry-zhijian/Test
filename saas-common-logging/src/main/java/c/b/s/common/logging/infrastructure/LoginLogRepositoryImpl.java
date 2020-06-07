package c.b.s.common.logging.infrastructure;

import c.b.s.common.logging.domain.LoginLog;
import c.b.s.common.logging.domain.LoginLogRepository;
import c.b.s.common.logging.infrastructure.jooq.tables.records.CasLoginLogRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static c.b.s.common.logging.infrastructure.jooq.Tables.CAS_LOGIN_LOG;
import static c.b.s.common.logging.infrastructure.jooq.tables.CasLoginLog.*;

/**
 * Created by guiqingqing on 2018/8/11.
 */
public class LoginLogRepositoryImpl implements LoginLogRepository {
    @Autowired
    private DSLContext dsl;

    @Override
    public int save(LoginLog loginLog) {
        InsertSetMoreStep insertSetMoreStep = dsl.insertInto(CAS_LOGIN_LOG)
                .set(USER_ID, ULong.valueOf(loginLog.getUserId()))
                .set(USER_NAME, loginLog.getUserName())
                .set(REAL_NAME, loginLog.getRealName())
                .set(TYPE, UByte.valueOf(loginLog.getType()));
        if (Objects.nonNull(loginLog.getIp())) {
            insertSetMoreStep.set(IP, loginLog.getIp());
        }
        Record record = insertSetMoreStep.returning().fetchOne();
        loginLog.setId(((CasLoginLogRecord) record).getId().longValue());
        return 1;
    }

    @Override
    public List<LoginLog> findByUserId(int pageNum, int pageSize, Long userId) {
        return findByPage(pageNum, pageSize, userId, null, null);
    }

    @Override
    public int countByUserId(Long userId) {
        return count(userId, null, null);
    }

    @Override
    public List<LoginLog> findByUserName(int pageNum, int pageSize, String userName) {
        return findByPage(pageNum, pageSize, null, userName, null);
    }

    @Override
    public int countByUserName(String userName) {
        return count(null, userName, null);
    }

    @Override
    public List<LoginLog> findByRealName(int pageNum, int pageSize, String realName) {
        return findByPage(pageNum, pageSize, null, null, realName);
    }

    @Override
    public int countByRealName(String realName) {
        return count(null, null, realName);
    }

    private List<LoginLog> findByPage(int pageNum, int pageSize, Long userId, String userName, String realName) {
        SortField sortField = DSL.field(INSERTTIME).desc();
        SelectConditionStep selectConditionStep = dsl.selectFrom(CAS_LOGIN_LOG)
                .where(ISACTIVE.eq(Byte.valueOf("1")));
        if (Objects.nonNull(userId)) {
            selectConditionStep.and(USER_ID.eq(ULong.valueOf(userId)));
        }
        if (Objects.nonNull(userName)) {
            selectConditionStep.and(USER_NAME.eq(userName));
        }
        if (Objects.nonNull(realName)) {
            selectConditionStep.and(REAL_NAME.eq(realName));
        }
        Result<CasLoginLogRecord> records = selectConditionStep.orderBy(sortField)
                .limit(pageSize)
                .offset((pageNum - 1) * pageSize)
                .fetch();
        return records.stream()
                .map(record -> toEntity(record))
                .collect(Collectors.toList());
    }

    private int count(Long userId, String userName, String realName) {
        SelectConditionStep selectConditionStep = dsl.selectCount()
                .from(CAS_LOGIN_LOG)
                .where(ISACTIVE.eq(Byte.valueOf("1")));
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

    private LoginLog toEntity(CasLoginLogRecord record) {
        return LoginLog.builder()
                .id(record.getId().longValue())
                .userId(record.getUserId().longValue())
                .userName(record.getUserName())
                .realName(record.getRealName())
                .ip(record.getIp())
                .type(record.getType().byteValue())
                .insertTime(record.getInserttime())
                .build();
    }
}