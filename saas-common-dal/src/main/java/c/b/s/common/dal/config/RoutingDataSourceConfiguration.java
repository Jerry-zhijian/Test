package c.b.s.common.dal.config;

import c.b.s.common.dal.datasource.RoutingDataSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created: 2018-08-09 17:44:53
 * 
 * @author  Michael.Zhang
 */
@Configuration
@ConditionalOnWebApplication
@ComponentScan(
        value = "c.b.s.common.dal",
        excludeFilters = {@ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                value = DataSourceAutoConfiguration.class
        )}
)
public class RoutingDataSourceConfiguration {

    @Autowired
    private RoutingDataSourceBuilder builder;

    @Bean
    @ConditionalOnMissingBean
    public DataSource getDataSource() throws SQLException {
        return builder.build();
    }

}
