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
public class ProjectManager extends Users {
    private String projectManagerID;
    
    public ProjectManager(int userID, String name, String username, String password, String email, String phoneNumber, String projectManagerID) {
        super (userID, name, username, password, email, phoneNumber);
        this.projectManagerID = projectManagerID;
    }
    
    public ProjectManager(String name, String username, String password, String email, String phoneNumber, String projectManagerID) {
        super (name, username, password, email, phoneNumber);
        this.projectManagerID = projectManagerID;
    }

    public String getProjectManagerID() {
        return projectManagerID;
    }

    public void setProjectManagerID(String projectManagerID) {
        this.projectManagerID = projectManagerID;
    }

    
    
}
