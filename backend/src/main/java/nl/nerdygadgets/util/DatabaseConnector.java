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

    /**
     * Maak de datasource aan
     */
    private void initializeDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://web0157.zxcs.nl/u127250p176374_nerdygadgets");
        basicDataSource.setUsername("u127250p176374_nerdygadgets");
        basicDataSource.setPassword("QpO2kV8Tq9U42cOC!");

        // Configure connection pool properties
        basicDataSource.setInitialSize(5); // Initial number of connections
        basicDataSource.setMaxTotal(100);   // Maximum number of connections
        basicDataSource.setMaxIdle(5);     // Maximum number of idle connections
        basicDataSource.setMinIdle(2);     // Minimum number of idle connections

        dataSource = basicDataSource;
    }

    /**
     * Voer een query uit op de database
     * @param query De query die uitgevoerd moet worden
     * @return Het resultaat van de query
     * @throws SQLException Als er een fout optreedt bij het uitvoeren van de query
     */
    public ResultSet query(String query) throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    /**
     * Voer een SQL statement uit op de database
     * @param sql De SQL statement die uitgevoerd moet worden
     * @throws SQLException Als er een fout optreedt bij het uitvoeren van de SQL statement
     */
    public void execute(String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    /**
     * Haal een connectie op uit de datasource
     * @return De connectie
     * @throws SQLException Als er een fout optreedt bij het ophalen van de connectie
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Sluit alle variabelen die open staan die met een database query te maken hebben
     * @param stmt De statement die gesloten moet worden
     * @param rs De resultset die gesloten moet worden
     * @param connection De connectie die gesloten moet worden
     */
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
