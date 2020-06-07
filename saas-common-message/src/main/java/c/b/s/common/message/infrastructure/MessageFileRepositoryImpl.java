package c.b.s.common.message.infrastructure;

import c.b.s.common.message.domain.MessageFile;
import c.b.s.common.message.domain.MessageFileRepository;
import c.b.s.common.message.infrastructure.jooq.tables.records.TbMesageFileRecord;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static c.b.s.common.message.infrastructure.jooq.Tables.TB_MESAGE_FILE;
import static c.b.s.common.message.infrastructure.jooq.tables.TbMesageFile.*;

public class MessageFileRepositoryImpl implements MessageFileRepository {

    @Autowired
    private DSLContext dsl;

    @Override
    public int save(MessageFile messageFile) {
        InsertSetMoreStep insertSetMoreStep = dsl.insertInto(TB_MESAGE_FILE)
                .set(MESSAGE_ID, messageFile.getMessageId())
                .set(FILE_NAME, messageFile.getFileName())
                .set(INSERTTIME, new Timestamp(new Date().getTime()))
                .set(UPDATETIME, new Timestamp(new Date().getTime()))
                .set(ISACTIVE, Byte.valueOf("1"));

        Record record = insertSetMoreStep.returning().fetchOne();
        messageFile.setId(((TbMesageFileRecord) record).getId().longValue());
        return 1;
    }

    @Override
    public List<MessageFile> findMessageFileByMessageId(Long messageId) {
        Result<TbMesageFileRecord> records = dsl.selectFrom(TB_MESAGE_FILE)
                .where(ISACTIVE.eq(Byte.valueOf("1")))
                .and(MESSAGE_ID.eq(messageId))
                .fetch();
        return records.stream()
                .map(record -> toEntity(record))
                .collect(Collectors.toList());
    }

    private MessageFile toEntity(TbMesageFileRecord record) {
        return MessageFile.builder()
                .id(record.getId().longValue())
                .messageId(record.getMessageId())
                .fileName(record.getFileName())
                .inserttime(record.getInserttime())
                .updatetime(record.getUpdatetime())
                .build();
    }

}