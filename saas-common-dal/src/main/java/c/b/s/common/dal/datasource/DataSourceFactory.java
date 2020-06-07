package c.b.s.common.dal.datasource;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Create datasource with data source factory.
 *
 * Created: 2018-07-30 11:05:51
 * 
 * @author  Michael.Zhang
 */
@Slf4j
public class DataSourceFactory {

    private static final String DRUID_DS_POOL = "com.alibaba.druid.pool.DruidDataSource";
    private static final String TOMCAT_DS_POOL = "org.apache.tomcat.jdbc.pool.DataSource";
    private static final String SIMPLE_DS_POOL = "org.springframework.jdbc.datasource.SimpleDriverDataSource";

    private static final String DEFAULT_DATE_SOURCE_TYPE = DRUID_DS_POOL;


    public static DataSource create(DataSourceMeta meta) throws SQLException {
        Preconditions.checkNotNull(meta, "DataSourceMeta must be not null!");
        Preconditions.checkNotNull(meta.getRouteId(), "RouteId must be not null!");

        DataSourceBuilder builder = doSelectBuilder(meta.getType());
        if (builder == null) {
            throw new RuntimeException("Unsupported data source type: " + meta.getType());
        }

        DataSource dataSource = builder.build(meta);

        log.info("Init dataSource successful: builder={}, dataSourceMeta={}", builder.getClass(), meta);

        return dataSource;
    }

    private static DataSourceBuilder doSelectBuilder(String type) {
        String dsType = trim(type);
        if (dsType == null || dsType.isEmpty()) {
            dsType = DEFAULT_DATE_SOURCE_TYPE;
            log.warn("Data source type is empty,use default:{}", DEFAULT_DATE_SOURCE_TYPE);
        }

        switch (dsType) {
            case DRUID_DS_POOL:
                return new DruidDataSourceBuilder();
            case TOMCAT_DS_POOL:
                return new TomcatDataSourceBuilder();
            case SIMPLE_DS_POOL:
                return new SimpleDataSourceBuilder();
            default:
                return null;
        }

    }

    private static String trim(String value) {
        return value == null ? null : value.trim();
    }

}