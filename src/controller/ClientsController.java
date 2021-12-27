/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static dao.DBClients.dbAddClient;
import static dao.DBClients.dbDeleteClient;
import static dao.DBClients.dbUpdateClient;
import static dao.DBClients.getAllClients;
import static dao.DBJobInvoice.dbDeleteJobItem;
import static dao.DBJobInvoice.getAllJobItems;
import static dao.DBJobs.dbDeleteJob;
import static dao.DBJobs.getAllJobs;
import static dao.DBUsers.getAllSalesmen;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Client;
import model.Job;
import model.JobInvoice;
import model.Salesman;
import utils.Alerts;

/**
 * FXML Controller class
 *
 * @author indya
 */
public class ClientsController implements Initializable {

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<Client> clientTable;
    @FXML
    private TableColumn<Client, String> phoneNumberColumn;
    @FXML
    private TableColumn<Client, String> emailColumn;
    @FXML
    private TableColumn<Client, String> salesmanColumn;
    @FXML
    private TableColumn<Client, String> firstNameColumn;
    @FXML
    private TableColumn<Client, String> lastNameColumn;
    @FXML
    private Button searchButton;
    
    private boolean modified = false;
    private Client clientToModify;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modified = false;
        clientTable.setItems(getAllClients());
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        salesmanColumn.setCellValueFactory(new PropertyValueFactory<>("salesmanID"));
        
    }    

    @FXML
    private void searchButton(ActionEvent event) {
        String keyWord = searchTextField.getText();
        ObservableList<Client> searchResults = FXCollections.observableArrayList();
        if (keyWord == "") {
            clientTable.setItems(getAllClients());
        } else {
            for (Client c : getAllClients()) {
                if (c.getLastName().contains(keyWord)) {
                    searchResults.add(c);
                }
            }
            clientTable.setItems(searchResults);
        }
    }

    @FXML
    private void addButton(ActionEvent event) {
        try {
            String firstNameInput = firstNameTextField.getText();
            String lastNameInput = lastNameTextField.getText();
            String phoneNumberInput = phoneNumberTextField.getText();
            String emailInput = emailTextField.getText();
            if (firstNameInput.equals("") || lastNameInput.equals("") || phoneNumberInput.equals("") || emailInput.equals("")) {
                Alerts.errorAlerts(2);
            }
            String salesmanID = null;
            for (Salesman s : getAllSalesmen()) {
                if (s.getUserID() == LoginController.currentUser.getUserID()) {
                    salesmanID = s.getSalesmanID();
                }
            }
            if (modified == false) {
                Client clientToAdd = new Client(firstNameInput, lastNameInput, phoneNumberInput, emailInput, salesmanID);
                dbAddClient(clientToAdd);
                modified = false;
                clearAllFields();
                clientTable.setItems(getAllClients());
            } else if (modified == true) {
                Client clientToAdd = new Client(clientToModify.getClientID(), firstNameInput, lastNameInput, phoneNumberInput, emailInput, salesmanID);
                dbUpdateClient(clientToAdd);
                modified = false;
                clearAllFields();
                clientTable.setItems(getAllClients());
            }
        } catch (Exception e) {
            Alerts.errorAlerts(2);
        }
    }

    @FXML
    private void editButton(ActionEvent event) {
        clientToModify = clientTable.getSelectionModel().getSelectedItem();
        if (clientToModify == null) {
            Alerts.errorAlerts(5);
        } else {
            firstNameTextField.setText(clientToModify.getFirstName());
            lastNameTextField.setText(clientToModify.getLastName());
            phoneNumberTextField.setText(clientToModify.getPhoneNumber());
            emailTextField.setText(clientToModify.getEmail());
            modified = true;
        }
    }

    @FXML
    private void deleteButton(ActionEvent event) throws SQLException {
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            Alerts.errorAlerts(4);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Client Confirmation");
            alert.setHeaderText("Are you sure you would like to remove this client? This will also remove any associated jobs.");
            alert.setContentText("Press OK if you would like to remove this client, otherwise press Cancel");
            alert.showAndWait();
        
            if (alert.getResult() == ButtonType.OK) {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Confirmation");
                alert2.setHeaderText("This client and all associated jobs have been deleted.");
                alert2.setContentText("Press OK to continue.");
                alert2.showAndWait();
                    for (Job j : getAllJobs()) {
                        if (j.getClientID() == selectedClient.getClientID()) {
                            for (JobInvoice ji : getAllJobItems()) {
                                if (j.getJobID() == ji.getJobID()) {
                                    dbDeleteJobItem(ji);
                                }
                            }
                            dbDeleteJob(j);
                        }
                    }
                    dbDeleteClient(selectedClient);
                    clientTable.setItems(getAllClients());
            } else {
                alert.close();
            }
        }
    }

    @FXML
    private void backButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }
    
    private void clearAllFields() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        emailTextField.clear();
        phoneNumberTextField.clear();
        searchTextField.clear();
        modified = false;
        clientToModify = null;
        clientTable.setItems(getAllClients());
    }

    @FXML
    private void clearButtonAction(ActionEvent event) {
        clearAllFields();
    }

    @FXML
    private void enterPressed(ActionEvent a) {
        searchButton.fire();
    }
    
}
