package c.b.s.common.message.interfaces.dto;

import c.p.b.api.request.Request;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by guiqingqing on 2018/8/18.
 */
@Data
@NoArgsConstructor
public class QueryUnreadMessageDTO extends Request {
    private Integer readCount;
}