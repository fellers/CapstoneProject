/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Job;
import model.JobInvoice;

/**
 *
 * @author indya
 */
public class DBJobInvoice {
    public static ObservableList<JobInvoice> getAllJobItems() {
        ObservableList<JobInvoice> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * from job_invoice";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int invoiceID = rs.getInt("invoice_id");
                int jobID = rs.getInt("job_id");
                int productID = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                String modelNumber = rs.getString("model_number");
                BigDecimal price = rs.getBigDecimal("price");
                String category = rs.getString("category");
                String brand = rs.getString("brand");
                JobInvoice j = new JobInvoice(invoiceID, jobID, productID, quantity, modelNumber, price.setScale(2), category, brand);
                clist.add(j);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
    
    public static ObservableList<JobInvoice> getNonJobItems(int job, int product) {
        ObservableList<JobInvoice> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * from job_invoice where job_id != ? AND product_id != ?";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setInt(1, job);
            ps.setInt(2, product);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int invoiceID = rs.getInt("invoice_id");
                int jobID = rs.getInt("job_id");
                int productID = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                String modelNumber = rs.getString("model_number");
                BigDecimal price = rs.getBigDecimal("price");
                String category = rs.getString("category");
                String brand = rs.getString("brand");
                JobInvoice j = new JobInvoice(invoiceID, jobID, productID, quantity, modelNumber, price.setScale(2), category, brand);
                clist.add(j);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
    
    public static void dbAddJobItem(JobInvoice j) throws SQLException {
        try {
            String sql = "INSERT INTO job_invoice (job_id, product_id, quantity, model_number, price, category, brand) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setInt(1, j.getJobID());
            ps.setInt(2, j.getProductID());
            ps.setInt(3, j.getQuantity());
            ps.setString(4, j.getModelNumber());
            ps.setBigDecimal(5, j.getPrice().setScale(2));
            ps.setString(6, j.getCategory());
            ps.setString(7, j.getBrand());
            
            ps.executeUpdate();
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public static void dbDeleteJobItem(JobInvoice j) throws SQLException {
        try {
            String sql = "DELETE from job_invoice where invoice_id = " + j.getRowID();
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.executeUpdate();
            
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    }
    
    public static void dbUpdateJobItem(JobInvoice j) throws SQLException {
        try {
            String sql = "UPDATE job_invoice set quantity = ? where invoice_id = ?";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setInt(1, j.getQuantity());
            ps.setInt(2, j.getRowID());
            
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public static ObservableList<JobInvoice> getItemsForJob(Job job) {
        ObservableList<JobInvoice> clist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from job_invoice where job_id = ?";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setInt(1, job.getJobID());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int invoiceID = rs.getInt("invoice_id");
                int jobID = rs.getInt("job_id");
                int productID = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                String modelNumber = rs.getString("model_number");
                BigDecimal price = rs.getBigDecimal("price");
                String category = rs.getString("category");
                String brand = rs.getString("brand");
                JobInvoice j = new JobInvoice(invoiceID, jobID, productID, quantity, modelNumber, price.setScale(2), category, brand);
                clist.add(j);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
    
    public static BigDecimal getSalesmanSales(int id) {
        BigDecimal total = new BigDecimal(0).setScale(2);
        
        
        return total;
    }
}
