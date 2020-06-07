package c.b.s.common.dal.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.logging.log4j.util.Strings;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Build datasource with ali druid datasource pool.
 *
 * Created: 2018-07-30 11:05:51
 * 
 * @author  Michael.Zhang
 */
public class DruidDataSourceBuilder implements DataSourceBuilder {

    @Override
    public DataSource build(DataSourceMeta meta) throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();

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
            dataSource.setTimeBetweenEvictionRunsMillis(meta.getTimeBetweenEvictionRunsMillis());
        }
        if (meta.getMinEvictableIdleTimeMillis() != null) {
            dataSource.setMinEvictableIdleTimeMillis(meta.getMinEvictableIdleTimeMillis());
        }
        if (meta.getMaxEvictableIdleTimeMillis() != null) {
            dataSource.setMaxEvictableIdleTimeMillis(meta.getMaxEvictableIdleTimeMillis());
        }
        if (meta.getPoolPreparedStatements() != null) {
            dataSource.setPoolPreparedStatements(meta.getPoolPreparedStatements());
        }
        if (meta.getMaxPoolPreparedStatementPerConnectionSize() != null) {
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(meta.getMaxPoolPreparedStatementPerConnectionSize());
        }
        if (meta.getFilters() != null) {
            dataSource.setFilters(meta.getFilters());
        }

        return dataSource;
    }

}