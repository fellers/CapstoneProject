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
import model.Product;

/**
 *
 * @author indya
 */
public class DBProducts {
    public static ObservableList<Product> getAllProducts() {
        ObservableList<Product> clist = FXCollections.observableArrayList();
        
        try {
            String sql = "SELECT * from products";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int productID = rs.getInt("product_id");
                String modelNumber = rs.getString("model_number");
                BigDecimal price = rs.getBigDecimal("price");
                String category = rs.getString("category");
                String brand = rs.getString("brand");
                Product p = new Product(productID, modelNumber, price.setScale(2), category, brand);
                clist.add(p);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clist;
    }
    
    public static void dbAddProduct(Product p) throws SQLException {
        try {
            String sql = "INSERT INTO products (model_number, price, category, brand) VALUES (?, ?, ?, ?)";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setString(1, p.getModelNumber());
            ps.setBigDecimal(2, p.getPrice().setScale(2));
            ps.setString(3, p.getCategory());
            ps.setString(4, p.getBrand());
            
            ps.executeUpdate();
            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
    public static void dbDeleteProduct(Product p) throws SQLException {
        try {
            String sql = "DELETE from products where product_id = " + p.getProductID();
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.executeUpdate();
            
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    }
    
    public static void dbUpdateProduct(Product p) throws SQLException {
        try {
            String sql = "UPDATE products set model_number = ?, price = ?, category = ?, brand = ? where product_id = ?";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            
            ps.setString(1, p.getModelNumber());
            ps.setBigDecimal(2, p.getPrice().setScale(2));
            ps.setString(3, p.getCategory());
            ps.setString(4, p.getBrand());
            ps.setInt(5, p.getProductID());
            
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    
}
