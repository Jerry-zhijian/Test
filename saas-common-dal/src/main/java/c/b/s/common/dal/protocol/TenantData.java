package c.b.s.common.dal.protocol;

import c.b.s.common.dal.datasource.DataSourceMeta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 租户数据配置信息。
 *
 * Created 2018-08-28 13:28:23
 * 
 * @author Michael.Zhang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantData {

    private Long    tenantId;

    private String  url;
    private String  driverClassName;
    private String  username;
    private String  password;

    private String  type;
    private Integer initialSize;
    private Integer maxActive;
    private Integer minIdle;
    private Integer maxWait;
    private String  validationQuery;
    private Integer validationQueryTimeout;
    private Boolean testOnBorrow;
    private Boolean testOnReturn;
    private Boolean testWhileIdle;
    private Long    timeBetweenEvictionRunsMillis;
    private Long    minEvictableIdleTimeMillis;
    private Long    maxEvictableIdleTimeMillis;
    private Boolean poolPreparedStatements;
    private Integer maxPoolPreparedStatementPerConnectionSize;
    private String  filters;
    private Date    lastModifiedTime;


    public DataSourceMeta toDataSourceMeta() {
        DataSourceMeta meta = new DataSourceMeta();
        meta.setRouteId(getTenantId().toString());
        BeanUtils.copyProperties(this, meta);

        return meta;
    }

}
