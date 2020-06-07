package c.b.s.common.message.domain;

import java.util.List;

/**
 * Created by guiqingqing on 2018/8/11.
 */
public interface MessageRepository {
    int save(Message message);

    List<Message> findUnReadByUser(Long userId, Long applicationId, int count);

    int countUnRead(Long userId, Long applicationId);

    List<Message> findByUser(Long userId, Long applicationId, int pageNum, int pageSize, String orderColumn, String orderBy);

    int count(Long userId, Long applicationId);

    int read(Long messageId);

    Message messageOfId(Long messageId);
}