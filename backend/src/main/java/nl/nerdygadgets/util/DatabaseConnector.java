package nl.nerdygadgets.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import nl.nerdygadgets.util.Log;

public class DatabaseConnector {

    private DataSource dataSource;

    public DatabaseConnector() {
        initializeDataSource();
    }

    private void initializeDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://web0157.zxcs.nl");
        basicDataSource.setUsername("u127250p176374_nerdygadgets");
        basicDataSource.setPassword("QpO2kV8Tq9U42cOC!");

        // Configure connection pool properties
        basicDataSource.setInitialSize(5); // Initial number of connections
        basicDataSource.setMaxTotal(10);   // Maximum number of connections
        basicDataSource.setMaxIdle(5);     // Maximum number of idle connections
        basicDataSource.setMinIdle(2);     // Minimum number of idle connections

        dataSource = basicDataSource;
    }

    public ResultSet query(String query) {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            if (statement.execute(query)) {
                return statement.getResultSet();
            }
        } catch (SQLException ex) {
            Log.logger.warning("SQLException: " + ex.getMessage());
            Log.logger.warning("SQLState: " + ex.getSQLState());
            Log.logger.warning("VendorError: " + ex.getErrorCode());
        }
        return null;
    }
}
