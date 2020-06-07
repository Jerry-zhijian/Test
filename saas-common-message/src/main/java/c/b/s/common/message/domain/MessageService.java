package c.b.s.common.message.domain;

import c.b.s.common.message.interfaces.dto.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by guiqingqing on 2018/8/18.
 */
@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public PagingResponse<Message> findByPage(Long userId, Long applicationId, int pageNum, int pageSize, String orderColumn, String orderBy) {
        List<Message> messages = messageRepository.findByUser(userId, applicationId, pageNum, pageSize, orderColumn, orderBy);
        int totalCount = messageRepository.count(userId, applicationId);
        PagingResponse<Message> response = new PagingResponse(totalCount, messages);
        return response;
    }

    public PagingResponse<Message> findUnread(Long userId, Long applicationId, Integer count) {
        List<Message> messages = messageRepository.findUnReadByUser(userId, applicationId, count);
        int totalCount = messageRepository.countUnRead(userId, applicationId);
        PagingResponse<Message> response = new PagingResponse(totalCount, messages);
        return response;
    }

    public void read(Long id) {
        messageRepository.read(id);
    }
}