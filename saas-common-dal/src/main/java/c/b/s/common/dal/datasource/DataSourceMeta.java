package c.b.s.common.dal.datasource;

import lombok.*;

import java.util.Date;

/**
 * Datasource metadata info.
 * 
 * Created: 2018-07-30 11:08:42
 * 
 * @author  Michael.Zhang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"username", "password"})
public class DataSourceMeta {

    private String  routeId;

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

    public boolean isDiff(DataSourceMeta lastMeta) {
        return lastMeta == null || !lastModifiedTime.equals(lastMeta.getLastModifiedTime());
    }

}