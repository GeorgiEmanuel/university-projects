package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * Responsible for establishing the connection to the database.
 * Uses JDBC ana the singleton pattern.
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/schooldb";
    private static final String USER = "root";
    private static final String PASSWORD = "root1234PT";

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new connection to the MySql database using the url, user and password.
     *
     * @return Connection
     */
    private Connection createConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            LOGGER.warning("Error creating connection: " + e.getMessage());
            return null;
        }
    }

    /**
     * Provides a new database connection.
     *
     * @return Connection
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Closes the connection, warning message if the connection fails to close.
     *
     * @param connection the created connection
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.warning("Failed to close connection: " + e.getMessage());
            }
        }
    }
    /**
     * Closes the statement, warning message if the statement fails to close.
     *
     * @param statement the executed statement
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.warning("Failed to close statement: " + e.getMessage());
            }
        }
    }

    /**
     * Closes the resultSet, warning message if the statement fails to close.
     *
     * @param resultSet the ResultSet to be closed
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.warning("Failed to close result set: " + e.getMessage());
            }
        }
    }
}
