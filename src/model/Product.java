/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import static dao.DBProducts.getAllProducts;
import java.math.BigDecimal;

/**
 *
 * @author indya
 */
public class Product {
    private int productID;
    private String modelNumber;
    private BigDecimal price;
    private String category;
    private String brand;
    
    

    public Product(int productID, String modelNumber, BigDecimal price, String category, String brand) {
        this.productID = productID;
        this.modelNumber = modelNumber;
        this.price = price;
        this.category = category;
        this.brand = brand;
    }

    public Product(String modelNumber, BigDecimal price, String category, String brand) {
        this.modelNumber = modelNumber;
        this.price = price;
        this.category = category;
        this.brand = brand;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public static BigDecimal getProductPrice(JobInvoice ji) {
        BigDecimal price = new BigDecimal(0);
        for (Product p : getAllProducts()) {
            if (p.getProductID() == ji.getProductID()) {
                price = p.getPrice();
            }
        }
        return price.setScale(2);
    }
    
    
}
