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
import model.Admin;
import model.ProjectManager;
import model.Salesman;

/**
 *
 * @author indya
 */
public class DBUsers {
    public static ObservableList<Salesman> getAllSalesmen() {
        ObservableList<Salesman> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * from salesmen";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int userID = rs.getInt("User_ID");
                String name = rs.getString("name");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone");
                String salesmanID = rs.getString("salesman_id");
                Salesman s = new Salesman(userID, name, userName, password, email, phoneNumber, salesmanID);
                clist.add(s);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
    
    public static void dbAddSalesman(Salesman s) throws SQLException {
        try {
            String sql = "INSERT INTO salesmen (name, user_name, password, email, phone, salesman_id) VALUES (?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setString(1, s.getName());
            ps.setString(2, s.getUsername());
            ps.setString(3, s.getPassword());
            ps.setString(4, s.getEmail());
            ps.setString(5, s.getPhoneNumber());
            ps.setString(6, s.getSalesmanID());
            
            
            ps.executeUpdate();
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public static void dbDeleteSalesman(Salesman s) throws SQLException {
        try {
            String sql = "DELETE from salesmen where user_id = " + s.getUserID();
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.executeUpdate();
            
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    }
    
    public static void dbUpdateSalesman(Salesman s) throws SQLException {
        try {
            String sql = "UPDATE salesmen set name = ?, user_name = ?, password = ?, email = ?, phone = ?, salesman_id = ? where user_id = ?";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setString(1, s.getName());
            ps.setString(2, s.getUsername());
            ps.setString(3, s.getPassword());
            ps.setString(4, s.getEmail());
            ps.setString(5, s.getPhoneNumber());
            ps.setString(6, s.getSalesmanID());
            ps.setInt(7, s.getUserID());
            
            ps.executeUpdate();
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public static ObservableList<ProjectManager> getAllProjectManagers() {
        ObservableList<ProjectManager> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * from project_managers";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int userID = rs.getInt("User_ID");
                String name = rs.getString("name");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone");
                String projectManagerID = rs.getString("project_manager_id");
                ProjectManager p = new ProjectManager(userID, name, userName, password, email, phoneNumber, projectManagerID);
                clist.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
    
    public static void dbAddProjectManager(ProjectManager p) throws SQLException {
        try {
            String sql = "INSERT INTO project_managers (name, user_name, password, email, phone, project_manager_id) VALUES (?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setString(1, p.getName());
            ps.setString(2, p.getUsername());
            ps.setString(3, p.getPassword());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getPhoneNumber());
            ps.setString(6, p.getProjectManagerID());
            
            
            ps.executeUpdate();
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public static void dbDeleteProjectManager(ProjectManager p) throws SQLException {
        try {
            String sql = "DELETE from project_managers where user_id = " + p.getUserID();
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.executeUpdate();
            
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    }
    
    public static void dbUpdateProjectManager(ProjectManager p) throws SQLException {
        try {
            String sql = "UPDATE project_managers set name = ?, user_name = ?, password = ?, email = ?, phone = ?, project_manager_id = ? where user_id = ?";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setString(1, p.getName());
            ps.setString(2, p.getUsername());
            ps.setString(3, p.getPassword());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getPhoneNumber());
            ps.setInt(6, p.getUserID());
            
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public static ObservableList<Admin> getAllAdmins() {
        ObservableList<Admin> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * from admin";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int userID = rs.getInt("id");
                String name = rs.getString("name");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone");
                Admin a = new Admin(userID, name, userName, password, email, phoneNumber);
                clist.add(a);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
}
