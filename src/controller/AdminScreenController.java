/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static dao.DBClients.getAllClients;
import static dao.DBJobs.getAllJobs;
import static dao.DBUsers.dbAddProjectManager;
import static dao.DBUsers.dbAddSalesman;
import static dao.DBUsers.dbDeleteProjectManager;
import static dao.DBUsers.dbDeleteSalesman;
import static dao.DBUsers.dbUpdateProjectManager;
import static dao.DBUsers.dbUpdateSalesman;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Client;
import model.Job;
import model.ProjectManager;
import model.Salesman;
import utils.Alerts;

/**
 * FXML Controller class
 *
 * @author indya
 */
public class AdminScreenController implements Initializable {

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private ComboBox<String> userTypeComboBox;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private ComboBox<Salesman> salesmanComboBox;
    @FXML
    private ComboBox<ProjectManager> projectManagersComboBox;
    @FXML
    private TextField uniqueIDTextField;
    
    String nameInput;
    String usernameInput;
    String passwordInput;
    String userTypeInput;
    String emailInput;
    String phoneInput;
    String uniqueIDInput;
    
    private boolean modified = false;
    private Salesman salesmanToEdit;
    private ProjectManager projectManagerToEdit;
    private Salesman salesmanToDelete;
    private ProjectManager projectManagerToDelete;
    
    ObservableList<String> userTypes = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userTypes.addAll("Salesman", "Project Manager");
        userTypeComboBox.setItems(userTypes);
        salesmanComboBox.setItems(getAllSalesmen());
        projectManagersComboBox.setItems(getAllProjectManagers());
    }    

    @FXML
    private void cancelButton(ActionEvent event) throws IOException {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Confirmation");
        a.setHeaderText("Exiting this screen will log you out.");
        a.setContentText("Are you sure you would like to continue?");
        a.showAndWait();
        if (a.getResult() == ButtonType.OK) {
            Alerts.informationAlerts(1);
            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login Screen");
            stage.show();
        }
    }

    @FXML
    private void saveButton(ActionEvent event) {
        try {
            nameInput = nameTextField.getText();
            usernameInput = usernameTextField.getText();
            passwordInput = passwordTextField.getText();
            userTypeInput = userTypeComboBox.getValue();
            emailInput = emailTextField.getText();
            phoneInput = phoneNumberTextField.getText();
            uniqueIDInput = uniqueIDTextField.getText();
            if (modified == false) {
                if (userTypeInput.equals("Salesman")) {
                    Salesman s = new Salesman(nameInput, usernameInput, passwordInput, emailInput, phoneInput, uniqueIDInput);
                    dbAddSalesman(s);
                    clearAllFields();
                    salesmanComboBox.setItems(getAllSalesmen());
                } else if (userTypeInput.equals("Project Manager")) {
                    ProjectManager p = new ProjectManager(nameInput, usernameInput, passwordInput, emailInput, phoneInput, uniqueIDInput);
                    dbAddProjectManager(p);
                    clearAllFields();
                    projectManagersComboBox.setItems(getAllProjectManagers());
                }
            } else if (modified == true) {
                if (userTypeInput.equals("Salesman")) {
                    Salesman s = new Salesman(salesmanToEdit.getUserID(), nameInput, usernameInput, passwordInput, emailInput, phoneInput, uniqueIDInput);
                    dbUpdateSalesman(s);
                    clearAllFields();
                    salesmanComboBox.setItems(getAllSalesmen());
                    modified = false;
                } else if (userTypeInput.equals("Project Manager")) {
                    ProjectManager p = new ProjectManager(projectManagerToEdit.getUserID(), nameInput, usernameInput, passwordInput, emailInput, phoneInput, uniqueIDInput);
                    dbUpdateProjectManager(p);
                    clearAllFields();
                    projectManagersComboBox.setItems(getAllProjectManagers());
                    modified = false;
                }
            }
        } catch (Exception e) {
            Alerts.errorAlerts(2);
        }
    }
    
    private void clearAllFields() {
        nameTextField.clear();
        usernameTextField.clear();
        passwordTextField.clear();
        userTypeComboBox.itemsProperty().setValue(null);
        emailTextField.clear();
        phoneNumberTextField.clear();
        uniqueIDTextField.clear();
        salesmanComboBox.itemsProperty().setValue(null);
        projectManagersComboBox.itemsProperty().setValue(null);
        userTypeComboBox.setItems(userTypes);
        salesmanComboBox.setItems(getAllSalesmen());
        projectManagersComboBox.setItems(getAllProjectManagers());
        modified = false;
        userTypeComboBox.setDisable(false);
    }

    @FXML
    private void editSalesmanButton(ActionEvent event) {
        try {
            modified = true;
            userTypeComboBox.setDisable(modified);
            salesmanToEdit = salesmanComboBox.getValue();
            nameTextField.setText(salesmanToEdit.getName());
            usernameTextField.setText(salesmanToEdit.getUsername());
            passwordTextField.setText(salesmanToEdit.getPassword());
            userTypeComboBox.setValue("Salesman");
            emailTextField.setText(salesmanToEdit.getEmail());
            phoneNumberTextField.setText(salesmanToEdit.getPhoneNumber());
            uniqueIDTextField.setText(salesmanToEdit.getSalesmanID());
            
        } catch (Exception e) {
            Alerts.errorAlerts(3);
        }
    }

    @FXML
    private void removeSalesmanButton(ActionEvent event) {
        boolean cannotDelete = false;
        try {
            salesmanToDelete = salesmanComboBox.getValue();
            for (Client c : getAllClients()) {
                if (c.getSalesmanID().equals(salesmanToDelete.getSalesmanID())) {
                    cannotDelete = true;
                }
            }
            if (cannotDelete == true) {
                Alerts.errorAlerts(11);
            } else {
                Alert a = new Alert(AlertType.CONFIRMATION);
                a.setTitle("Confirm Delete");
                a.setHeaderText("Are you sure you would like to delete this salesman?");
                a.setContentText("This action cannot be undone.");
                a.showAndWait();
                if (a.getResult() == ButtonType.OK) {
                    dbDeleteSalesman(salesmanToDelete);
                    Alerts.informationAlerts(2);
                    clearAllFields();
                }
            }

        } catch (Exception e) {
            Alerts.errorAlerts(3);
        }
    }

    @FXML
    private void editProjectManager(ActionEvent event) {
        try {
            modified = true;
            userTypeComboBox.setDisable(modified);
            projectManagerToEdit = projectManagersComboBox.getValue();
        } catch (Exception e) {
            Alerts.errorAlerts(3);
        }
    }

    @FXML
    private void removeProjectManager(ActionEvent event) {
        boolean cannotDelete = false;
        try {
            projectManagerToDelete = projectManagersComboBox.getValue();
            for (Job j : getAllJobs()) {
                if (j.getProjectManagerID().equals(projectManagerToDelete.getProjectManagerID())) {
                    cannotDelete = true;
                }
            }

            if (cannotDelete == true) {
                Alerts.errorAlerts(12);
            } else {
                Alert a = new Alert(AlertType.CONFIRMATION);
                a.setTitle("Confirm Delete");
                a.setHeaderText("Are you sure you would like to delete this project manager?");
                a.setContentText("This action cannot be undone.");
                a.showAndWait();
                if (a.getResult() == ButtonType.OK) {
                    dbDeleteProjectManager(projectManagerToDelete);
                    Alerts.informationAlerts(3);
                    clearAllFields();
                }
            }
        } catch (Exception e) {
            Alerts.errorAlerts(3);
        }
    }

    @FXML
    private void clearButtonAction(ActionEvent event) {
        clearAllFields();
    }
    
    
}
