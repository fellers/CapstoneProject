/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static dao.DBJobs.getAllJobs;
import static dao.DBUsers.getAllProjectManagers;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Job;
import model.ProjectManager;
import utils.Alerts;

/**
 * FXML Controller class
 *
 * @author indya
 */
public class ProjectManagerReportsController implements Initializable {

    @FXML
    private ComboBox<ProjectManager> projectManagerReportsComboBox;
    @FXML
    private TableView<Job> jobsTable;
    @FXML
    private TableColumn<Job, String> jobNameColumn;
    @FXML
    private TableColumn<Job, String> clientColumn;
    @FXML
    private TableColumn<Job, Date> finalInstallDateColumn;
    @FXML
    private TableColumn<Job, String> statusColumn;
    @FXML
    private Label jobsInProgressLabel;
    @FXML
    private Label jobsCompletedLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        projectManagerReportsComboBox.setItems(getAllProjectManagers());
        
        jobNameColumn.setCellValueFactory(new PropertyValueFactory<>("jobName"));
        clientColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Job, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Job, String> param) {
                return new SimpleStringProperty(Job.getClientName(param.getValue().getClientID()));
            }
        });
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
        finalInstallDateColumn.setCellValueFactory(new PropertyValueFactory<>("finalInstallDate"));
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
    private void getReportsButtonAction(ActionEvent event) {
        try {
            ProjectManager selected = projectManagerReportsComboBox.getValue();
            ObservableList<Job> jobList = FXCollections.observableArrayList();
            for (Job j : getAllJobs()) {
                if (j.getProjectManagerID().equals(selected.getProjectManagerID())) {
                    jobList.add(j);
                }
            }
            jobsTable.setItems(jobList);
            int jobsInProgress = 0;
            int completedJobs = 0;
            ObservableList<Job> completed = FXCollections.observableArrayList();
            ObservableList<Job> inProgress = FXCollections.observableArrayList();
            for (Job j : jobList) {
                if (j.getProgress().equals("Completed")) {
                    completed.add(j);
                } else {
                    inProgress.add(j);
                }
            }
            jobsInProgress = inProgress.size();
            completedJobs = completed.size();
            jobsInProgressLabel.setText(Integer.toString(jobsInProgress));
            jobsCompletedLabel.setText(Integer.toString(completedJobs));
        } catch (Exception e) {
            Alerts.errorAlerts(14);
        }
    }
    
}
