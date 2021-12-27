/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static dao.DBJobInvoice.getItemsForJob;
import static dao.DBJobs.getAllJobs;
import static dao.DBUsers.getAllProjectManagers;
import static dao.DBUsers.getAllSalesmen;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import static model.Job.getTotalJobCost;
import model.JobInvoice;
import model.Product;
import model.ProjectManager;
import model.Salesman;
import utils.Alerts;

/**
 * FXML Controller class
 *
 * @author indya
 */
public class JobReportsController implements Initializable {

    @FXML
    private TableView<JobInvoice> jobItemsTable;
    @FXML
    private TableColumn<JobInvoice, String> modelNumberColumn;
    @FXML
    private TableColumn<JobInvoice, String> priceColumn;
    @FXML
    private TableColumn<JobInvoice, Integer> quantityColumn;
    @FXML
    private Label salesmanLabel;
    @FXML
    private Label projectManagerLabel;
    @FXML
    private Label totalCostLabel;
    @FXML
    private ComboBox<Job> jobsComboBox;
    @FXML
    private Label statusLabel;
    @FXML
    private Label lastEditLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        jobsComboBox.setItems(getAllJobs());
        
        modelNumberColumn.setCellValueFactory(new PropertyValueFactory<>("modelNumber"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<JobInvoice, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<JobInvoice, String> param) {
                    return new SimpleStringProperty("$" + Product.getProductPrice(param.getValue()).toString());
                }
            });
    }    


    @FXML
    private void getReportButton(ActionEvent event) {
        try {
            Job selected = jobsComboBox.getValue();
            jobItemsTable.setItems(getItemsForJob(selected));
            for (ProjectManager p : getAllProjectManagers()) {
                if (p.getProjectManagerID().equals(selected.getProjectManagerID())) {
                    projectManagerLabel.setText(p.getName());
                }
            }
            for (Salesman s : getAllSalesmen()) {
                if (s.getSalesmanID().equals(selected.getSalesmanID())) {
                    salesmanLabel.setText(s.getName());
                }
            }
            totalCostLabel.setText("$" + getTotalJobCost(selected.getJobID()).toString());
            statusLabel.setText(selected.getProgress());
            lastEditLabel.setText(selected.getLastEdit().toString());
        } catch (Exception e) {
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
