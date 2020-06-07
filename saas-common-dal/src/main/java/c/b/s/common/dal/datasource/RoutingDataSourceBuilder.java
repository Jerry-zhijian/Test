package c.b.s.common.dal.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Routing datasource builder.
 * 
 * Created: 2018-07-30 11:23:02
 * 
 * @author  Michael.Zhang
 */
@Component
public class RoutingDataSourceBuilder {

    @Autowired
    private DataSourceMetaService metaService;

    public DataSource build() throws SQLException {
        RoutingDataSource routingDataSource = new RoutingDataSource();

        List<DataSourceMeta> dataSourceMetas = metaService.getDataSourceMetas();
        DataSourceMetaCache.clearPutDataSourceMetas(dataSourceMetas);

        Map<Object, DataSource> dataSourceMap = new HashMap<>();

        DataSource defaultDataSource = null;
        for (DataSourceMeta dataSourceMeta : dataSourceMetas) {
            DataSource druidDataSource = DataSourceFactory.create(dataSourceMeta);
            if (defaultDataSource == null) {
                defaultDataSource = druidDataSource;
            }
            dataSourceMap.put(dataSourceMeta.getRouteId(), druidDataSource);
        }

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(new UnsupportedDataSource(defaultDataSource));

        return routingDataSource;
    }

}