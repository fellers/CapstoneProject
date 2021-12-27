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
import java.sql.Timestamp;
import java.util.Date;
import model.Job;

/**
 *
 * @author indya
 */
public class DBJobs {
    public static ObservableList<Job> getAllJobs() {
        ObservableList<Job> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * from jobs";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int jobID = rs.getInt("job_id");
                String jobName = rs.getString("job_name");
                String progress = rs.getString("progress");
                String jobNotes = rs.getString("job_notes");
                Timestamp createDate = rs.getTimestamp("create_date");
                Timestamp lastEdit = rs.getTimestamp("last_edit");
                String lastEditBy = rs.getString("last_edit_by");
                Date finalInstallDate = rs.getDate("final_install_date");
                String address = rs.getString("address");
                String city = rs.getString("city");
                Long zip = rs.getLong("zip");
                int clientID = rs.getInt("client_id");
                String salesmanID = rs.getString("salesman_id");
                String projectManagerID = rs.getString("project_manager_id");
                Job j = new Job(jobID, jobName, progress, jobNotes, createDate, lastEdit, lastEditBy, finalInstallDate, address, city, zip, clientID, salesmanID, projectManagerID);
                clist.add(j);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
    
    public static void dbAddJob(Job j) throws SQLException {
        try {
            String sql = "INSERT INTO jobs (job_name, progress, job_notes, create_date, last_edit, last_edit_by, final_install_date, address, city, zip, client_id, salesman_id, project_manager_id) VALUES (?, ?, ?, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setString(1, j.getJobName());
            ps.setString(2, j.getProgress());
            ps.setString(3, j.getJobNotes());
            ps.setString(4, j.getLastEditBy());
            ps.setDate(5, (java.sql.Date) j.getFinalInstallDate());
            ps.setString(6, j.getAddress());
            ps.setString(7, j.getCity());
            ps.setLong(8, j.getZip());
            ps.setInt(9, j.getClientID());
            ps.setString(10, j.getSalesmanID());
            ps.setString(11, j.getProjectManagerID());
            
            ps.executeUpdate();
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public static void dbDeleteJob(Job j) throws SQLException {
        try {
            String sql = "DELETE from jobs where job_id = " + j.getJobID();
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.executeUpdate();
            
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    }
    
    public static void dbUpdateJob(Job j) throws SQLException {
        try {
            String sql = "UPDATE jobs set job_name = ?, progress = ?, job_notes = ?, last_edit = CURRENT_TIMESTAMP(), last_edit_by = ?, final_install_date = ?, address = ?, city = ?, zip = ?, client_id = ?, salesman_id = ?, project_manager_id = ? where job_id = ?";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setString(1, j.getJobName());
            ps.setString(2, j.getProgress());
            ps.setString(3, j.getJobNotes());
            ps.setString(4, j.getLastEditBy());
            ps.setDate(5, (java.sql.Date) j.getFinalInstallDate());
            ps.setString(6, j.getAddress());
            ps.setString(7, j.getCity());
            ps.setLong(8, j.getZip());
            ps.setInt(9, j.getClientID());
            ps.setString(10, j.getSalesmanID());
            ps.setString(11, j.getProjectManagerID());
            ps.setInt(12, j.getJobID());
            
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
