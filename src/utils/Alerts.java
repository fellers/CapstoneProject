/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;

/**
 *
 * @author indya
 */
public class Alerts {
    public static void errorAlerts (int alertCase) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        switch (alertCase) {
            case 1:
                a.setTitle("Error");
                a.setHeaderText("Unsuccessful Login");
                a.setContentText("Username or password incorrect, please try again.");
                a.showAndWait();
                break;
            case 2:
                a.setTitle("Error");
                a.setHeaderText("Cannot save.");
                a.setContentText("You must fill out all fields.");
                a.showAndWait();
                break;
            case 3:
                a.setTitle("Error");
                a.setHeaderText("Cannot edit or delete.");
                a.setContentText("You must first make a selection.");
                a.showAndWait();
                break;
            case 4:
                a.setTitle("Error");
                a.setHeaderText("Cannot delete client.");
                a.setContentText("You must first make a selection");
                a.showAndWait();
                break;
            case 5:
                a.setTitle("Error");
                a.setHeaderText("Cannot edit client.");
                a.setContentText("You must first make a selection");
                a.showAndWait();
                break;
            case 6:
                a.setTitle("Error");
                a.setHeaderText("Cannot delete job.");
                a.setContentText("You must first make a selection");
                a.showAndWait();
                break;
            case 7:
                a.setTitle("Error");
                a.setHeaderText("Cannot edit job.");
                a.setContentText("You must first make a selection");
                a.showAndWait();
                break;
            case 8:
                a.setTitle("Error");
                a.setHeaderText("Cannot delete product.");
                a.setContentText("You must first make a selection");
                a.showAndWait();
                break;
            case 9:
                a.setTitle("Error");
                a.setHeaderText("Cannot edit product.");
                a.setContentText("You must first make a selection");
                a.showAndWait();
                break;
            case 10:
                a.setTitle("Error");
                a.setHeaderText("Cannot add job");
                a.setContentText("Please ensure that all fields are filled out correctly and that the job name is unique.");
                a.showAndWait();
                break;
            case 11:
                a.setTitle("Error");
                a.setHeaderText("Cannot remove products.");
                a.setContentText("The quantity you entered is greater than the quantity sold to this job.");
                a.showAndWait();
                break;
            case 12:
                a.setTitle("Error");
                a.setHeaderText("Cannot delete salesman");
                a.setContentText("You cannot delete a salesman that is assigned to jobs or clients.");
                a.showAndWait();
                break;
            case 13:
                a.setTitle("Error");
                a.setHeaderText("Cannot delete project manager");
                a.setContentText("You cannot delete project managers that are assigned to jobs.");
                a.showAndWait();
                break;
            case 14:
                a.setTitle("Error");
                a.setHeaderText("Cannot obtain reports");
                a.setContentText("You must first make a selection.");
                a.showAndWait();
                break;
            case 15:
                a.setTitle("Error");
                a.setHeaderText("Cannot edit job");
                a.setContentText("Once a job has been marked as completed you can no longer modify it.");
                a.showAndWait();
                break;
            case 16:
                a.setTitle("Error");
                a.setHeaderText("Cannot add products.");
                a.setContentText("You must first save the job.");
                a.showAndWait();
                break;
        }
    }
    
    public static void warningAlerts (int alertCase) {
        
    }
    
    public static void informationAlerts (int alertCase) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        switch (alertCase) {
            case 1:
                a.setTitle("Confirmation");
                a.setHeaderText("You have been logged out.");
                a.setContentText("You will now be returned to the login screen.");
                a.showAndWait();
                break;
            case 2:
                a.setTitle("Confirmation");
                a.setHeaderText("Salesman successfully deleted.");
                a.showAndWait();
                break;
            case 3:
                a.setTitle("Confirmation");
                a.setHeaderText("Project manager successfully deleted.");
                a.showAndWait();
                break;
            case 4:
                a.setTitle("Confirmation");
                a.setHeaderText("This Job has been successfully deleted.");
                a.showAndWait();
                break;
            case 5:
                a.setTitle("Confirmation");
                a.setHeaderText("Job successfully saved, you may now add products to it.");
                a.showAndWait();
                break;
            case 6:
                a.setTitle("Confirmation");
                a.setHeaderText("Job successfully saved.");
                a.showAndWait();
                break;
        }
    }
}
