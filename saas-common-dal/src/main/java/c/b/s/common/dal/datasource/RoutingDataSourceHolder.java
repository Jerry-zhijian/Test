package c.b.s.common.dal.datasource;

import com.google.common.base.Preconditions;

/**
 * Dynamic datasource holder.
 * 
 * Created: 2018-07-30 10:56:06
 * 
 * @author  Michael.Zhang
 */
public class RoutingDataSourceHolder {

    public static final ThreadLocal<Object> HOLDER = new ThreadLocal<>();

    public static Object getCurrentDataSourceKey() {
        return HOLDER.get();
    }

    public static void setDataSourceKey(Object dataSourceKey) {
        Preconditions.checkNotNull(dataSourceKey);
        HOLDER.set(dataSourceKey);
    }

    public static void removeDataSourceKey(Object dataSourceKey) {
        HOLDER.remove();
    }

}