package c.b.s.common.dal.datasource;

import c.b.s.common.dal.util.SpringContextUtils;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Data source reload monitor.
 *
 * Created 2018-09-17 13:56:01
 *
 * @author Michael.Zhang
 */
@Slf4j
@Component
public class RoutingDataSourceMonitor implements InitializingBean, DisposableBean {

    @Autowired
    private DataSourceMetaService metaService;

    private volatile boolean running;
    private static final int SCHEDULE_DELAY_TIME = 1 * 60 * 1000; // milliseconds
    private static final int MONITOR_INTERVAL_TIME = 3 * 60 * 1000; // milliseconds
    private static final int DATA_SOURCE_CLOSE_DELAY = 10 * 1000; // milliseconds
    private static final ScheduledExecutorService POOL = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void afterPropertiesSet() throws Exception {
        this.startMonitor();
        running = true;
        log.info("RoutingDataSourceMonitor started!");
    }

    @Override
    public void destroy() throws Exception {
        running =  false;
        this.destroyMonitor();
        log.info("RoutingDataSourceMonitor destroyed!");
    }

    public void startMonitor() {
        POOL.schedule(() -> {
            while (running) {
                try {
                    reloadDataSources();
                    Thread.sleep(MONITOR_INTERVAL_TIME);
                } catch (Exception ex) {
                    log.error("Reload data source error!", ex);
                }
            }
        }, SCHEDULE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    public void destroyMonitor() {
        POOL.shutdown();
    }

    private void reloadDataSources() throws SQLException {
        List<DataSourceMeta> currentMetaList = metaService.getDataSourceMetas();
        if (currentMetaList == null || currentMetaList.isEmpty()) {
            return;
        }

        List<DataSourceMeta> changedMataList = doGetChangedDataSources(currentMetaList);
        if (changedMataList == null || changedMataList.isEmpty()) {
            return;
        }

        List<DataSource> expiredDataSourceList = doReloadChangedDataSources(changedMataList);

        DataSourceMetaCache.clearPutDataSourceMetas(currentMetaList);

        doCloseExpiredDataSources(expiredDataSourceList);

        log.info("Data source modified, reload succeed: {}", changedMataList);
    }

    private List<DataSourceMeta> doGetChangedDataSources(List<DataSourceMeta> currentMetaList) {
        List<DataSourceMeta> changedMataList = null;
        for (DataSourceMeta nowMeta : currentMetaList) {
            DataSourceMeta oldMeta = DataSourceMetaCache.getDataSourceMeta(nowMeta.getRouteId());
            if (nowMeta.isDiff(oldMeta)) {
                if (changedMataList == null) {
                    changedMataList = new ArrayList<>();
                }
                changedMataList.add(nowMeta);
            }
        }

        return changedMataList;
    }

    private List<DataSource> doReloadChangedDataSources(List<DataSourceMeta> changedMetaList) throws SQLException  {
        final RoutingDataSource routingDataSource =  SpringContextUtils.getRoutingDataSource();
        final Map<Object, DataSource> routingDataSourceMap = routingDataSource.getTargetDataSources();

        List<DataSource> expiredDataSourceList = new ArrayList<>();
        for (DataSourceMeta diffMeta : changedMetaList) {
            String routeId = diffMeta.getRouteId();
            expiredDataSourceList.add(routingDataSourceMap.get(routeId));
            routingDataSourceMap.put(routeId, DataSourceFactory.create(diffMeta));
        }

        routingDataSource.setTargetDataSources(routingDataSourceMap);

        return expiredDataSourceList;
    }

    private void doCloseExpiredDataSources(List<DataSource> expiredList) {
        if (expiredList == null || expiredList.isEmpty()) {
            return;
        }

        try {
            Thread.sleep(DATA_SOURCE_CLOSE_DELAY);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        expiredList.forEach((dataSource -> {
            if (dataSource != null) {
                if (dataSource instanceof DruidDataSource) {
                    ((DruidDataSource) dataSource).close();
                } else if (dataSource instanceof org.apache.tomcat.jdbc.pool.DataSource) {
                    ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).close();
                } else if (dataSource instanceof SimpleDriverDataSource) {
                    // do nothing
                }
            }
        }));
    }

}
