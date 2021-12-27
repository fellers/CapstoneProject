/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static dao.DBJobInvoice.dbDeleteJobItem;
import static dao.DBJobInvoice.getAllJobItems;
import static dao.DBJobs.dbDeleteJob;
import static dao.DBJobs.getAllJobs;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import static javafx.scene.control.Alert.AlertType.CONFIRMATION;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Job;
import static model.Job.getClientName;
import model.JobInvoice;
import utils.Alerts;

/**
 * FXML Controller class
 *
 * @author indya
 */
public class JobListController implements Initializable {

    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<Job> jobsTable;
    @FXML
    private TableColumn<Job, String> clientColumn;
    @FXML
    private TableColumn<Job, String> addressColumn;
    @FXML
    private TableColumn<Job, String> progressColumn;
    @FXML
    private TableColumn<Job, String> salesmanColumn;
    @FXML
    private TableColumn<Job, String> projectManagerColumn;
    @FXML
    private Button searchButton;
    
    public static Job jobToEdit;
    public static boolean editJob = false;
    public static boolean addingNew = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        progressColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("jobName"));
//        clientColumn.setCellValueFactory(new Callback<CellDataFeatures<Job, String>, ObservableValue<String>>(){
//            @Override
//            public ObservableValue<String> call(CellDataFeatures<Job, String> param) {
//                return new SimpleStringProperty(Job.getClientName(param.getValue().getClientID()));
//            }
//        });
        salesmanColumn.setCellValueFactory(new Callback<CellDataFeatures<Job, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(CellDataFeatures<Job, String> param) {
                return new SimpleStringProperty(Job.getSalesmanName(param.getValue().getSalesmanID()));
            }
        });
        projectManagerColumn.setCellValueFactory(new Callback<CellDataFeatures<Job, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(CellDataFeatures<Job, String> param) {
                return new SimpleStringProperty(Job.getProjectManagerName(param.getValue().getProjectManagerID()));
            }
        });
        jobsTable.setItems(getAllJobs());
    }    

    @FXML
    private void searchButton(ActionEvent event) {
        String keyWord = searchTextField.getText();
        ObservableList<Job> searchResults = FXCollections.observableArrayList();
        if (keyWord == "") {
            jobsTable.setItems(getAllJobs());
        } else {
            for (Job j : getAllJobs()) {
                if (getClientName(j.getClientID()).contains(keyWord)) {
                    searchResults.add(j);
                }
            }
            jobsTable.setItems(searchResults);
        }
    }

    @FXML
    private void addButton(ActionEvent event) throws IOException {
        addingNew = true;
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddEditJob.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Add/Edit Jobs");
        stage.show();
        
    }

    @FXML
    private void editButton(ActionEvent event) throws IOException {
        jobToEdit = jobsTable.getSelectionModel().getSelectedItem();
        if (jobToEdit == null) {
            Alerts.errorAlerts(7);
        } else {
            editJob = true;
            Parent root = FXMLLoader.load(getClass().getResource("/view/AddEditJob.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Add/Edit Jobs");
            stage.show();
        }
    }

    @FXML
    private void backButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }

    @FXML
    private void deleteButton(ActionEvent event) throws SQLException {
        Alert a = new Alert(CONFIRMATION);
        a.setTitle("Confirm Delete");
        a.setHeaderText("Deleting this job will also remove any associated products.");
        a.setContentText("Are you sure you would like to continue?");
        a.showAndWait();
        if (a.getResult() == ButtonType.OK) {
            Job jobToDelete = jobsTable.getSelectionModel().getSelectedItem();
            if (jobToDelete == null) {
                Alerts.errorAlerts(6);
            } else {
                for (JobInvoice ji : getAllJobItems()) {
                    if (ji.getJobID() == jobToDelete.getJobID()) {
                        dbDeleteJobItem(ji);
                    }
                }
                dbDeleteJob(jobToDelete);
                Alerts.informationAlerts(4);
                jobsTable.setItems(getAllJobs());
            }
        }
    }

    @FXML
    private void enterPressed(ActionEvent event) {
        searchButton.fire();
    }
    
}
