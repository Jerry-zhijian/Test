package c.b.s.common.message.infrastructure;

import c.b.s.common.message.domain.Message;
import c.b.s.common.message.domain.MessageRepository;
import c.b.s.common.message.infrastructure.jooq.tables.records.TbMessageRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.types.UByte;
import org.jooq.types.ULong;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static c.b.s.common.message.infrastructure.jooq.Tables.TB_MESSAGE;
import static c.b.s.common.message.infrastructure.jooq.tables.TbMessage.*;

/**
 * Created by guiqingqing on 2018/8/11.
 */
public class MessageRepositoryImpl implements MessageRepository {
    @Autowired
    private DSLContext dsl;

    @Override
    public int save(Message message) {
        InsertSetMoreStep insertSetMoreStep = dsl.insertInto(TB_MESSAGE)
                .set(USER_ID, ULong.valueOf(message.getUserId()))
                .set(APPLICATION_ID, ULong.valueOf(message.getApplicationId()))
                .set(TYPE, UByte.valueOf(message.getType()))
                .set(SUBJECT, message.getSubject())
                .set(CONTENT, message.getContent())
                .set(FILE_NAME, message.getFileName());
        Record record = insertSetMoreStep.returning().fetchOne();
        message.setId(((TbMessageRecord) record).getId().longValue());
        return 1;
    }

    @Override
    public List<Message> findUnReadByUser(Long userId, Long applicationId, int count) {
        SortField sortField = DSL.field(INSERTTIME).desc();
        Result<TbMessageRecord> records = dsl.selectFrom(TB_MESSAGE)
                .where(ISACTIVE.eq(Byte.valueOf("1")))
                .and(STATUS.eq(UByte.valueOf("0"))) // 未读消息
                .and(USER_ID.eq(ULong.valueOf(userId)))
                .and(APPLICATION_ID.eq(ULong.valueOf(applicationId)))
                .orderBy(sortField)
                .limit(count)
                .fetch();
        return records.stream()
                .map(record -> toEntity(record))
                .collect(Collectors.toList());
    }

    @Override
    public int countUnRead(Long userId, Long applicationId) {
        return ((Integer) dsl.selectCount().from(TB_MESSAGE)
                .where(ISACTIVE.eq(Byte.valueOf("1")))
                .and(STATUS.eq(UByte.valueOf("0"))) // 未读消息
                .and(USER_ID.eq(ULong.valueOf(userId)))
                .and(APPLICATION_ID.eq(ULong.valueOf(applicationId)))
                .fetch(0).get(0)).intValue();
    }

    @Override
    public List<Message> findByUser(Long userId, Long applicationId, int pageNum, int pageSize, String orderColumn, String orderBy) {
        Result<TbMessageRecord> records = dsl.selectFrom(TB_MESSAGE)
                .where(ISACTIVE.eq(Byte.valueOf("1")))
                .and(USER_ID.eq(ULong.valueOf(userId)))
                .and(APPLICATION_ID.eq(ULong.valueOf(applicationId)))
                .orderBy(DSL.field(STATUS).asc(), DSL.field(INSERTTIME).desc())
                .limit(pageSize)
                .offset((pageNum - 1) * pageSize)
                .fetch();
        return records.stream()
                .map(record -> toEntity(record))
                .collect(Collectors.toList());
    }

    @Override
    public int count(Long userId, Long applicationId) {
        return ((Integer) dsl.selectCount().from(TB_MESSAGE)
                .where(ISACTIVE.eq(Byte.valueOf("1")))
                .and(USER_ID.eq(ULong.valueOf(userId)))
                .and(APPLICATION_ID.eq(ULong.valueOf(applicationId)))
                .fetch(0).get(0)).intValue();
    }

    @Override
    public int read(Long messageId) {
        return dsl.update(TB_MESSAGE)
                .set(STATUS, UByte.valueOf("1"))
                .where(ID.eq(ULong.valueOf(messageId)))
                .and(ISACTIVE.eq(Byte.valueOf("1")))
                .execute();
    }

    @Override
    public Message messageOfId(Long messageId) {
        TbMessageRecord record = dsl.selectFrom(TB_MESSAGE)
                .where(ID.eq(ULong.valueOf(messageId)))
                .and(ISACTIVE.eq(Byte.valueOf("1")))
                .fetchOne();
        return record == null ? null : toEntity(record);
    }

    private Message toEntity(TbMessageRecord record) {
        return Message.builder()
                .id(record.getId().longValue())
                .userId(record.getUserId().longValue())
                .applicationId(record.getApplicationId().longValue())
                .type(record.getType().byteValue())
                .subject(record.getSubject())
                .content(record.getContent())
                .fileName(record.getFileName())
                .status(record.getStatus().byteValue())
                .readTime("1".equals(record.getStatus().toString()) ? record.getReadtime() : null)
                .insertTime(record.getInserttime())
                .build();
    }
}