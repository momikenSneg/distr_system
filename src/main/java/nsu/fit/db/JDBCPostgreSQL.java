package nsu.fit.db;

import java.io.*;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class JDBCPostgreSQL implements AutoCloseable {

    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "123456";
    private static final String SQL_INIT = "init.sql";

    private static Connection connection;

    public static void connect() throws SQLException, IOException {
        connection = DriverManager.getConnection(DB_URL, USER, PASS);

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(SQL_INIT)).getFile());
        String sql = Files.readString(file.toPath());

        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.closeOnCompletion();
    }

    public static Connection getConnection() {
        return connection;
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) { // empty
            }
        }
    }
}
