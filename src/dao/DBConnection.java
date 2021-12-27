/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author indya
 */
public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//127.0.0.1:3306/";
    private static final String dbName = "wgu_capstone";    
    
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;
    
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    
    private static final String username = "root";
    private static final String password = "wgucapstone";
    private static Connection conn = null;
    
    /**
     * Initializes the database connection.
     */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
    
    /**
     * Closes the connection with the database.
     */
    public static void closeConnection() {
        try{
            conn.close();
        } catch (Exception e) {
            
        }
    }
    
    /**
     * Returns connection object.
     */
    public static Connection getConnection() {
        return conn;
    }
}
