package c.b.s.common.dal.datasource;

import org.springframework.jdbc.datasource.AbstractDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created 2018-09-17 15:18:30
 *
 * @author Michael.Zhang
 * @see    org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
 */
public abstract class AbstractRoutingDataSource extends AbstractDataSource {

    private Map<Object, DataSource> targetDataSources;
    private DataSource defaultTargetDataSource;

    public Map<Object, DataSource> getTargetDataSources() {
        return this.targetDataSources;
    }

    public void setTargetDataSources(Map<Object, DataSource> targetDataSources) {
        if (targetDataSources == null || targetDataSources.isEmpty()) {
            throw new IllegalArgumentException("'targetDataSources' must be not null or empty!");
        }

        this.targetDataSources = targetDataSources;
    }

    public void setDefaultTargetDataSource(DataSource defaultTargetDataSource) {
        this.defaultTargetDataSource = defaultTargetDataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return determineTargetDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineTargetDataSource().getConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if (iface.isInstance(this)) {
            return (T) this;
        }
        return determineTargetDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return iface.isInstance(this) || determineTargetDataSource().isWrapperFor(iface);
    }

    protected DataSource determineTargetDataSource() {
        Object lookupKey = determineCurrentLookupKey();
        DataSource dataSource = this.targetDataSources.get(lookupKey);
        if (dataSource == null && lookupKey == null) {
            dataSource = this.defaultTargetDataSource;
        }
        if (dataSource == null) {
            throw new IllegalStateException("Cannot determine target DataSource for lookup key [" + lookupKey + "]");
        }
        return dataSource;
    }

    protected abstract Object determineCurrentLookupKey();

}
