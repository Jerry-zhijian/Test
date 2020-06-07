package c.b.s.common.dal.datasource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

/**
 * Unsupported data source wrapper.
 *
 * Created 2018-09-14 15:10:37
 *
 * @author Michael.Zhang
 */
public class UnsupportedDataSource implements DataSource {

    private DataSource dataSource;

    public UnsupportedDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new UnsupportedConnection(this.dataSource.getConnection());
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return new UnsupportedConnection(this.dataSource.getConnection(username, password));
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.dataSource.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.dataSource.isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return this.dataSource.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.dataSource.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        this.dataSource.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return this.dataSource.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return this.dataSource.getParentLogger();
    }


    private class UnsupportedConnection implements Connection {

        private Connection connection;
        private static final String ERROR_MSG = "Unsupported data source, please bind route id of tenant!";

        public UnsupportedConnection(Connection connection) {
            this.connection = connection;
        }

        @Override
        public Statement createStatement() throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return this.connection.getAutoCommit();
        }

        @Override
        public void commit() throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public void rollback() throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public void close() throws SQLException {
            this.connection.close();
        }

        @Override
        public boolean isClosed() throws SQLException {
            return this.connection.isClosed();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return this.connection.getMetaData();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return this.connection.isReadOnly();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public String getCatalog() throws SQLException {
            return this.connection.getCatalog();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return this.connection.getTransactionIsolation();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return this.connection.getWarnings();
        }

        @Override
        public void clearWarnings() throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return this.connection.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public int getHoldability() throws SQLException {
            return this.connection.getHoldability();
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public Clob createClob() throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public Blob createBlob() throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public NClob createNClob() throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return this.connection.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            throw new UnsupportedOperationException(ERROR_MSG);
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            throw new UnsupportedOperationException(ERROR_MSG);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return this.connection.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return this.connection.getClientInfo();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public String getSchema() throws SQLException {
            return this.connection.getSchema();
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return this.connection.getNetworkTimeout();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            throw new SQLException(ERROR_MSG);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return this.connection.isWrapperFor(iface);
        }
    }

}
