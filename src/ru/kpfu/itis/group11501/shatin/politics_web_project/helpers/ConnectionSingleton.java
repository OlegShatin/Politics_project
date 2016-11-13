package ru.kpfu.itis.group11501.shatin.politics_web_project.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Oleg Shatin
 *         11-501
 */
public class ConnectionSingleton {
    private static Connection connection = null;
    public static Connection getConnection(){
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/politics_web_app",
                        "postgres", "postgres");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        } else return connection;
    }
}
