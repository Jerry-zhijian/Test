package c.b.s.common.dal.util;

import c.b.s.common.dal.datasource.RoutingDataSource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created 2018-09-17 16:19:13
 *
 * @author Michael.Zhang
 */
@Component("c.b.s.common.dal.util.SpringContextUtils")
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static RoutingDataSource getRoutingDataSource() {
        return (RoutingDataSource) getBean(DataSource.class);
    }

}
