package c.b.s.common.dal.datasource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Data source builder interface.
 * 
 * Created 2018-08-28 14:52:20
 * 
 * @author Michael.Zhang
 */
public interface DataSourceBuilder {

    DataSource build(DataSourceMeta meta) throws SQLException;

}