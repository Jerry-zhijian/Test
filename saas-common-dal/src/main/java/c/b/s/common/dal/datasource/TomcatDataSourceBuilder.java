package c.b.s.common.dal.datasource;

import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.jdbc.pool.DataSource;

import java.sql.SQLException;

/**
 * Created 2018-10-09 13:32:27
 *
 * @author Michael.Zhang
 */
public class TomcatDataSourceBuilder implements DataSourceBuilder {

    @Override
    public javax.sql.DataSource build(DataSourceMeta meta) throws SQLException {
        DataSource dataSource = new DataSource();

        if (Strings.isNotEmpty(meta.getDriverClassName())) {
            dataSource.setDriverClassName(meta.getDriverClassName());
        }

        dataSource.setUrl(meta.getUrl());
        dataSource.setUsername(meta.getUsername());
        dataSource.setPassword(meta.getPassword());

        if (meta.getInitialSize() != null) {
            dataSource.setInitialSize(meta.getInitialSize());
        }
        if (meta.getMaxActive() != null) {
            dataSource.setMaxActive(meta.getMaxActive());
        }
        if (meta.getMinIdle() != null) {
            dataSource.setMinIdle(meta.getMinIdle());
        }
        if (meta.getMaxWait()!= null) {
            dataSource.setMaxWait(meta.getMaxWait());
        }
        if (Strings.isNotEmpty(meta.getValidationQuery())) {
            dataSource.setValidationQuery(meta.getValidationQuery());
        }
        if (meta.getValidationQueryTimeout() != null) {
            dataSource.setValidationQueryTimeout(meta.getValidationQueryTimeout());
        }
        if (meta.getTestOnBorrow() != null) {
            dataSource.setTestOnBorrow(meta.getTestOnBorrow());
        }
        if (meta.getTestOnReturn() != null) {
            dataSource.setTestOnReturn(meta.getTestOnReturn());
        }
        if (meta.getTestWhileIdle() != null) {
            dataSource.setTestWhileIdle(meta.getTestWhileIdle());
        }
        if (meta.getTimeBetweenEvictionRunsMillis() != null) {
            dataSource.setTimeBetweenEvictionRunsMillis(meta.getTimeBetweenEvictionRunsMillis().intValue());
        }
        if (meta.getMinEvictableIdleTimeMillis() != null) {
            dataSource.setMinEvictableIdleTimeMillis(meta.getMinEvictableIdleTimeMillis().intValue());
        }

        return dataSource;
    }

}
