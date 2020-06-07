package c.b.s.common.dal.datasource;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created 2018-09-17 14:06:46
 *
 * @author Michael.Zhang
 */
public class DataSourceMetaCache {

    private static final Map<String, DataSourceMeta> CACHE = new ConcurrentHashMap<>();

    public static void clearPutDataSourceMetas(List<DataSourceMeta> metaList)  {
        if (metaList == null || metaList.isEmpty()) {
            return;
        }

        CACHE.clear();
        metaList.forEach((item) -> CACHE.put(item.getRouteId(), item));
    }

    public static DataSourceMeta getDataSourceMeta(String routeId) {
        return CACHE.get(routeId);
    }

}
