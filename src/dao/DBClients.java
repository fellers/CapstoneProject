/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Client;

/**
 *
 * @author indya
 */
public class DBClients {
    public static ObservableList<Client> getAllClients() {
        ObservableList<Client> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * from clients";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int clientID = rs.getInt("client_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String phoneNumber = rs.getString("phone");
                String email = rs.getString("email");
                String salesmanID = rs.getString("salesman_id");
                Client c = new Client(clientID, firstName, lastName, phoneNumber, email, salesmanID);
                clist.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
    
    public static void dbAddClient(Client c) throws SQLException {
        try {
            String sql = "INSERT INTO clients (first_name, last_name, phone, email, salesman_id) VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getPhoneNumber());
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getSalesmanID());
            
            ps.executeUpdate();
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public static void dbDeleteClient(Client c) throws SQLException {
        try {
            String sql = "DELETE from clients where client_id = " + c.getClientID();
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.executeUpdate();
            
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    }
    
    public static void dbUpdateClient(Client c) throws SQLException {
        try {
            String sql = "UPDATE clients set first_name = ?, last_name = ?, phone = ?, email = ?, salesman_id = ? where client_id = ?";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getPhoneNumber());
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getSalesmanID());
            ps.setInt(6, c.getClientID());
            
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
