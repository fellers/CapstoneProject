/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author indya
 */
public class Salesman extends Users {

    private String salesmanID;
    
    public Salesman(String name, String username, String password, String email, String phoneNumber, String salesmanID) {
        super (name, username, password, email, phoneNumber);
        this.salesmanID = salesmanID;
    }
    public Salesman(int userID, String name, String username, String password, String email, String phoneNumber, String salesmanID) {
        super (userID, name, username, password, email, phoneNumber);
        this.salesmanID = salesmanID;
    }
    

    public String getSalesmanID() {
        return salesmanID;
    }

    public void setSalesmanID(String salesmanID) {
        this.salesmanID = salesmanID;
    }
    
    
}
