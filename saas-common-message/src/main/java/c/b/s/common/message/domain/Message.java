package c.b.s.common.message.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by guiqingqing on 2018/8/11.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Long id;
    private Long userId;
    private Long applicationId;
    private Byte type;
    private String subject;
    private String content;
    private String fileName;
    private Byte status;
    private Date readTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date insertTime;
}