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
public class OperationLog {
    private Long id;
    private Long userId;
    private String userName;
    private String realName;
    private Long applicationId;
    private String ip;
    private String url;
    private String operationCode;
    private String operationValue;
    private Date insertTime;
}