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
public class Admin extends Users {
    
    public Admin(int userID, String name, String username, String password, String email, String phoneNumber) {
        super(userID, name, username, password, email, phoneNumber);
    }

    public Admin(String name, String username, String password, String email, String phoneNumber) {
        super(name, username, password, email, phoneNumber);
    }
    
    
}
