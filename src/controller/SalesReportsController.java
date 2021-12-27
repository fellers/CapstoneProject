/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static dao.DBJobInvoice.getAllJobItems;
import static dao.DBJobs.getAllJobs;
import static dao.DBUsers.getAllSalesmen;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Job;
import model.JobInvoice;
import model.Salesman;
import utils.Alerts;

/**
 * FXML Controller class
 *
 * @author indya
 */
public class SalesReportsController implements Initializable {

    @FXML
    private ComboBox<Salesman> salesmanComboBox;
    @FXML
    private TableView<Job> jobsTable;
    @FXML
    private TableColumn<Job, String> jobNameColumn;
    @FXML
    private TableColumn<Job, String> clientColumn;
    @FXML
    private TableColumn<Job, String> totalCostColumn;
    @FXML
    private Label totalSalesLabel;
    @FXML
    private Label totalJobsLabel;
    @FXML
    private TableColumn<Job, String> progressColumn;
    @FXML
    private TableColumn<Job, Timestamp> lastEditColumn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        salesmanComboBox.setItems(getAllSalesmen());
        
    }    

    @FXML
    private void getReportButton(ActionEvent event) {
        ObservableList<Job> jobList = FXCollections.observableArrayList();
        BigDecimal totalSales = new BigDecimal(0).setScale(2);
        try {
            Salesman selected = salesmanComboBox.getValue();

            for (Job j : getAllJobs()) {
                if (j.getSalesmanID().equals(selected.getSalesmanID())) {
                    jobList.add(j);
                }
            }
            
            jobNameColumn.setCellValueFactory(new PropertyValueFactory<>("jobName"));
            clientColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Job, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Job, String> param) {
                    return new SimpleStringProperty(Job.getClientName(param.getValue().getClientID()));
                }
            });
            totalCostColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Job, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Job, String> param) {
                    return new SimpleStringProperty("$" + Job.getTotalJobCost(param.getValue().getJobID()).toString());
                }
            });
            progressColumn.setCellValueFactory(new PropertyValueFactory<>("progress"));
            lastEditColumn.setCellValueFactory(new PropertyValueFactory<>("lastEdit"));
            
            jobsTable.setItems(jobList);
            
            for (Job j : jobList) {
                for(JobInvoice ji : getAllJobItems()) {
                    if (j.getJobID() == ji.getJobID()) {
                        BigDecimal productCost = ji.getPrice().multiply(BigDecimal.valueOf(ji.getQuantity())).setScale(2);
                        totalSales = totalSales.add(productCost);
                    }
                }
            }
            
            totalSalesLabel.setText("$" + totalSales.toString());
            
            totalJobsLabel.setText(Integer.toString(jobList.size()));
        } catch (NullPointerException e) {
            Alerts.errorAlerts(14);
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
    
}
