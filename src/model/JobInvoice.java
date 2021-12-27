/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigDecimal;

/**
 *
 * @author indya
 */
public class JobInvoice extends Product{
    private int rowID;
    private int jobID;
    private int quantity;

    public JobInvoice(int rowID, int jobID, int productID, int quantity, String modelNumber, BigDecimal price, String category, String brand) {
        super(productID, modelNumber, price, category, brand);
        this.rowID = rowID;
        this.jobID = jobID;
        this.quantity = quantity;
    }

    public JobInvoice(int jobID, int productID, int quantity, String modelNumber, BigDecimal price, String category, String brand) {
        super(productID, modelNumber, price, category, brand);
        this.jobID = jobID;
        this.quantity = quantity;
    }

    public int getRowID() {
        return rowID;
    }

    public void setRowID(int rowID) {
        this.rowID = rowID;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
