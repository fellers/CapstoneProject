/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DBConnectionTest {
    
    @Before
    public void setUp() {
        DBConnection.startConnection();
    }
    
    @After
    public void tearDown() {
        DBConnection.closeConnection();
    }

    @Test
    public void testStartConnection() {
        System.out.println("startConnection");
        Connection conn = DBConnection.startConnection();
        assertNotNull(conn);
    }

    @Test
    public void testCloseConnection() throws SQLException {
        System.out.println("closeConnection");
        Connection conn = DBConnection.getConnection();
        DBConnection.closeConnection();
        assertTrue(conn.isClosed());
    }

    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        Connection conn = DBConnection.getConnection();
        assertNotNull(conn);
    }
    
}
