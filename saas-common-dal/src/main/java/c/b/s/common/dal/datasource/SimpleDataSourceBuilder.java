package c.b.s.common.dal.datasource;

import org.apache.logging.log4j.util.Strings;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.Driver;

/**
 * Simple driver data source builder.
 * 
 * Created 2018-08-28 15:16:15
 * 
 * @author Michael.Zhang
 */
public class SimpleDataSourceBuilder implements DataSourceBuilder {

    @Override
    public DataSource build(DataSourceMeta meta) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        final String driver = meta.getDriverClassName();
        if (Strings.isNotEmpty(driver)) {
            try {
                dataSource.setDriverClass((Class<? extends Driver>) Class.forName(driver));
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Not found driver class name: " + driver);
            }
        }
        dataSource.setUrl(meta.getUrl());
        dataSource.setUsername(meta.getUsername());
        dataSource.setPassword(meta.getPassword());

        return dataSource;
    }

}
