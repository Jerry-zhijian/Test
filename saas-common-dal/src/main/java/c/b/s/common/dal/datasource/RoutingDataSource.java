package c.b.s.common.dal.datasource;

import org.springframework.lang.Nullable;

/**
 * Routing datasource.
 * 
 * Created: 2018-07-30 10:54:18
 * 
 * @author  Michael.Zhang
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        return RoutingDataSourceHolder.getCurrentDataSourceKey();
    }

}