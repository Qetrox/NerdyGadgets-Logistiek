package nl.nerdygadgets.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class DatabaseConnector {

    private DataSource dataSource;

    public DatabaseConnector() {
        initializeDataSource();
    }

    private void initializeDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://web0157.zxcs.nl");
        basicDataSource.setUsername("u127250p176374_nerdygadgets");
        basicDataSource.setPassword("QpO2kV8Tq9U42cOC!");

        // Configure connection pool properties
        basicDataSource.setInitialSize(5); // Initial number of connections
        basicDataSource.setMaxTotal(100);   // Maximum number of connections
        basicDataSource.setMaxIdle(5);     // Maximum number of idle connections
        basicDataSource.setMinIdle(2);     // Minimum number of idle connections

        dataSource = basicDataSource;
    }

    public ResultSet query(String query) throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public void execute(String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void CloseVars(Statement stmt, ResultSet rs, Connection connection) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
