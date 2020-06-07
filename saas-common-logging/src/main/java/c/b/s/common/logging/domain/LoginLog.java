package c.b.s.common.logging.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by guiqingqing on 2018/8/11.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginLog {
    private Long id;
    private Long userId;
    private String userName;
    private String realName;
    private String ip;
    private Byte type;
    private Date insertTime;
}