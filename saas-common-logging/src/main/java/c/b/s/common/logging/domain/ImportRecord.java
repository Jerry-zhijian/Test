package c.b.s.common.logging.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by guiqingqing on 2018/8/29.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportRecord {
    private Long id;
    private String taskId;
    private Long assetId;
    private String fileName;
    private Byte importType;
    private String originalFileUrl;
    private String handledFileUrl;
    private Byte status;
    private Integer totalCount;
    private Integer successCount;
    private Long adminId;
    private String adminRealName;
    private Date insertTime;
    private Integer newUserCount;
    private Integer oldUserCount;
    private Integer totalUserCount;
}