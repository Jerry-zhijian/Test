package c.b.s.common.message.interfaces;

import c.b.s.common.message.domain.Message;
import c.b.s.common.message.domain.MessageService;
import c.b.s.common.message.interfaces.dto.PagingMessageByUserDTO;
import c.b.s.common.message.interfaces.dto.PagingResponse;
import c.b.s.common.message.interfaces.dto.QueryUnreadMessageDTO;
import c.b.s.common.util.UserInfoUtil;
import c.p.b.api.packaging.ResponsePackagingRestController;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by guiqingqing on 2018/8/18.
 */
@ResponsePackagingRestController
@RequestMapping("/message")
@Api(description = "消息服务接口")
public class MessageApi {
    @Autowired
    private MessageService messageService;

    @PostMapping("/findByPage")
    public PagingResponse<Message> findByPage(@RequestBody PagingMessageByUserDTO pagingMessageByUserDTO) {
        Preconditions.checkNotNull(pagingMessageByUserDTO);
        return messageService.findByPage(UserInfoUtil.getUserId(), UserInfoUtil.getApplicationId(),
                pagingMessageByUserDTO.getPageNum(), pagingMessageByUserDTO.getPageSize(),
                pagingMessageByUserDTO.getOrderByColumn(), pagingMessageByUserDTO.getOrderByType());
    }

    @PostMapping("/findUnread")
    public PagingResponse<Message> findUnread(@RequestBody QueryUnreadMessageDTO queryUnreadMessageDTO) {
        Preconditions.checkNotNull(queryUnreadMessageDTO);
        return messageService.findUnread(UserInfoUtil.getUserId(), UserInfoUtil.getApplicationId(), queryUnreadMessageDTO.getReadCount());
    }

    @PostMapping("/read")
    public Long read(@RequestBody Long id) {
        Preconditions.checkNotNull(id);
        messageService.read(id);
        return id;
    }
}