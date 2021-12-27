/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static dao.DBUsers.getAllAdmins;
import static dao.DBUsers.getAllProjectManagers;
import static dao.DBUsers.getAllSalesmen;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Admin;
import model.ProjectManager;
import model.Salesman;
import model.Users;
import utils.Alerts;
/**
 * FXML Controller class
 *
 * @author indya
 */
public class LoginController implements Initializable {

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    
    public static Users currentUser;
    @FXML
    private Button loginButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        currentUser = null;
    }
    
    @FXML
    public void onEnter(ActionEvent a) {
        loginButton.fire();
    }

    @FXML
    private void exitButtonAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void loginButtonAction(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        boolean successfulLoginCombo = false;
        
        //Create and populate list of all users
        ObservableList<Users> allUsers = FXCollections.observableArrayList();
        for (Salesman s : getAllSalesmen()) {
            allUsers.add(s);
        }
        for (Admin a : getAllAdmins()) {
            allUsers.add(a);
        }
        for (ProjectManager p : getAllProjectManagers()) {
            allUsers.add(p);
        }
        
        //Iterate through users, checking username and password combo and user type
        for(Users u : allUsers) {
            if (username.equals(u.getUsername()) && password.equals(u.getPassword())) {
                successfulLoginCombo = true;
                currentUser = u;
                if (u.getClass().equals(Salesman.class)) {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Main Menu");
                    stage.show();
                } else if(u.getClass().equals(Admin.class)) {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/AdminScreen.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Admin Menu");
                    stage.show();
                } else if(u.getClass().equals(ProjectManager.class)) {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Main Menu");
                    stage.show();
                }
            }
        }
        if (successfulLoginCombo == false) {
            Alerts.errorAlerts(1);
        }
    }
}
