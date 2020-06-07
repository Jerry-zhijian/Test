package c.b.s.common.message.domain;

import java.util.List;

public interface MessageFileRepository {

     int save(MessageFile messageFile);

     List<MessageFile> findMessageFileByMessageId(Long messageId);


}