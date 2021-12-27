/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.JobListController.jobToEdit;
import static dao.DBClients.getAllClients;
import dao.DBJobInvoice;
import static dao.DBJobInvoice.dbDeleteJobItem;
import static dao.DBJobInvoice.dbUpdateJobItem;
import static dao.DBJobInvoice.getAllJobItems;
import static dao.DBJobInvoice.getItemsForJob;
import dao.DBJobs;
import static dao.DBJobs.getAllJobs;
import static dao.DBProducts.getAllProducts;
import static dao.DBUsers.getAllProjectManagers;
import static dao.DBUsers.getAllSalesmen;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
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
import static javafx.scene.control.Alert.AlertType.CONFIRMATION;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Client;
import model.Job;
import static model.Job.extractDate;
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
public class AddEditJobController implements Initializable {

    @FXML
    private TextField jobNameTextField;
    @FXML
    private ComboBox<Client> clientComboBox;
    @FXML
    private DatePicker finalInstallDatePicker;
    @FXML
    private ComboBox<ProjectManager> projectManagerComboBox;
    @FXML
    private ComboBox<String> currentStatusComboBox;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField zipCodeTextField;
    @FXML
    private TextArea jobNotesTextArea;
    @FXML
    private TextField addProductsTableSearchTextField;
    @FXML
    private ComboBox<String> addProductsTableSearchComboBox;
    @FXML
    private TextField addProductsTableQuantityTextField;
    @FXML
    private Button addProductButton;
    @FXML
    private TableView<Product> addProductsTable;
    @FXML
    private TableColumn<Product, String> addProductsTableModelNumber;
    @FXML
    private TableColumn<Product, String> addProductsTableCategory;
    @FXML
    private TableColumn<Product, String> addProductsTableBrand;
    @FXML
    private TableColumn<Product, BigDecimal> addProductsTablePrice;
    @FXML
    private TextField productsSoldTableSearchTextField;
    @FXML
    private ComboBox<String> productsSoldTableSearchComboBox;
    @FXML
    private TextField productsSoldTableQuantityTextField;
    @FXML
    private Button removeProductButton;
    @FXML
    private TableView<JobInvoice> productsSoldTable;
    @FXML
    private TableColumn<JobInvoice, String> productsSoldTableModelNumber;
    @FXML
    private TableColumn<JobInvoice, String> productsSoldTableCategory;
    @FXML
    private TableColumn<JobInvoice, String> productsSoldTableBrand;
    @FXML
    private TableColumn<JobInvoice, BigDecimal> productsSoldTablePrice;
    @FXML
    private TableColumn<JobInvoice, Integer> productsSoldTableQuantity;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveJobButton;
    
    //Job object if an edit is being made
    private Job currentJob = null;
    //List of products that can be added to the job
    private ObservableList<Product> productsToAdd = FXCollections.observableArrayList();
    //List of products (JobInvoice objects) that have been added to the job
    private ObservableList<JobInvoice> productsAdded = FXCollections.observableArrayList();
    private ObservableList<String> searchFilters = FXCollections.observableArrayList();
    private ObservableList<String> statuses = FXCollections.observableArrayList();
    private String currentJobName = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        addProductsTableModelNumber.setCellValueFactory(new PropertyValueFactory<>("modelNumber"));
        addProductsTableCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        addProductsTableBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        addProductsTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productsSoldTableModelNumber.setCellValueFactory(new PropertyValueFactory<>("modelNumber"));
        productsSoldTableCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        productsSoldTableBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        productsSoldTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsSoldTableQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        try {
            currentJob = JobListController.jobToEdit;
            if (currentJob.getProgress().equals("Completed")) {
                removeProductButton.setDisable(true);
                addProductButton.setDisable(true);
                saveJobButton.setDisable(true);
                jobNameTextField.setEditable(false);
                addressTextField.setEditable(false);
                cityTextField.setEditable(false);
                zipCodeTextField.setEditable(false);
                jobNotesTextArea.setEditable(false);
                clientComboBox.setDisable(true);
                projectManagerComboBox.setDisable(true);
                currentStatusComboBox.setDisable(true);
                finalInstallDatePicker.setDisable(true);
                addProductsTableQuantityTextField.setEditable(false);
                productsSoldTableQuantityTextField.setEditable(false);
            }
            if (getItemsForJob(jobToEdit).isEmpty()) {
                productsToAdd = getAllProducts();
                addProductsTable.setItems(productsToAdd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (currentJob != null) {
            for (JobInvoice j : getAllJobItems()) {
                if (j.getJobID() == currentJob.getJobID()) {
                    productsAdded.add(j);
                }
            }
            for (Product p : getAllProducts()) {
                boolean addProduct = false;
                for (JobInvoice j : productsAdded) {
                    if (j.getProductID() == p.getProductID()) {
                        addProduct = false;
                        break;
                    } else {
                        addProduct = true;
                    }
                }
                if (addProduct == true) {
                    productsToAdd.add(p);
                }
            }
            Client jobClient = null;
            for (Client c : getAllClients()) {
                if (c.getClientID() == currentJob.getClientID()) {
                    jobClient = c;
                }
            }
            ProjectManager jobPM = null;
            for (ProjectManager p : getAllProjectManagers()) {
                if (p.getProjectManagerID().equals(currentJob.getProjectManagerID())) {
                    jobPM = p;
                }
            }
            jobNameTextField.setText(currentJob.getJobName());
            clientComboBox.setValue(jobClient);
            projectManagerComboBox.setValue(jobPM);
            currentStatusComboBox.setValue(currentJob.getProgress());
            addressTextField.setText(currentJob.getAddress());
            cityTextField.setText(currentJob.getCity());
            zipCodeTextField.setText(String.valueOf(currentJob.getZip()));
            finalInstallDatePicker.setValue(extractDate(currentJob.getFinalInstallDate()));
            jobNotesTextArea.setText(currentJob.getJobNotes());
            addProductsTable.setItems(productsToAdd);
        } else {
            productsToAdd = getAllProducts();
            addProductsTable.setItems(productsToAdd);
        }
        if (JobListController.addingNew == true) {
            addProductsTable.setItems(getAllProducts());
        } else {
            addProductsTable.setItems(productsToAdd);
        }

        
        productsSoldTable.setItems(productsAdded);

        
        statuses.addAll("Pre-Wire", "Trimout", "Final Install", "Completed");
        searchFilters.addAll("Model Number", "Brand");
        clientComboBox.setItems(getAllClients());
        projectManagerComboBox.setItems(getAllProjectManagers());
        addProductsTableSearchComboBox.setItems(searchFilters);
        productsSoldTableSearchComboBox.setItems(searchFilters);
        currentStatusComboBox.setItems(statuses);
    }    

    @FXML
    private void addProductsTableSearchButton(ActionEvent event) {
        String searchText = addProductsTableSearchTextField.getText();
        ObservableList<Product> searchResults = FXCollections.observableArrayList();
        if (searchText.equals("")) {
            searchResults = productsToAdd;
        } else if (addProductsTableSearchComboBox.getValue().equals("Brand")) {
            for (Product p : productsToAdd) {
                if (p.getBrand().contains(searchText)) {
                    searchResults.add(p);
                }
            }
        } else {
            for (Product p : productsToAdd) {
                if (p.getModelNumber().contains(searchText)) {
                    searchResults.add(p);
                }
            }
        }
        addProductsTable.setItems(searchResults);
    }

    @FXML
    private void productsSoldTableSearchButton(ActionEvent event) {
        String searchText = productsSoldTableSearchTextField.getText();
        ObservableList searchResults = FXCollections.observableArrayList();
        
        if (searchText.equals("")) {
            searchResults = productsAdded;
        } else if (productsSoldTableSearchComboBox.getValue().equals("Brand")) {
            for (JobInvoice j : productsAdded) {
                if (j.getBrand().contains(searchText)) {
                    searchResults.add(j);
                }
            }
        } else {
            for (JobInvoice j : productsAdded) {
                if (j.getModelNumber().contains(searchText)) {
                    searchResults.add(j);
                }
            }
        }
        productsSoldTable.setItems(productsAdded);
    }


    @FXML
    private void saveJobButton(ActionEvent event) {
        try {
        Salesman currentSalesman;
        String jobName = jobNameTextField.getText();
        String address = addressTextField.getText();
        String city = cityTextField.getText();
        Long zipCode = Long.parseLong(zipCodeTextField.getText());
        int clientID = clientComboBox.getValue().getClientID();
        String pmID = projectManagerComboBox.getValue().getProjectManagerID();
        String status = currentStatusComboBox.getValue();
        Date installDate = Date.valueOf(finalInstallDatePicker.getValue());
        String lastEditBy = null;
        String notes = jobNotesTextArea.getText();
        String salesmanID = null;
        int userID = LoginController.currentUser.getUserID();
        for (Salesman s : getAllSalesmen()) {
            if (s.getUserID() == userID) {
                currentSalesman = s;
                salesmanID = s.getSalesmanID();
                lastEditBy = s.getName();
            }
        }
        
        Job jobToAdd = new Job(jobName, status, notes, null, null, lastEditBy, installDate, address, city, zipCode, clientID, salesmanID, pmID);
        currentJobName = jobName;

            if (JobListController.editJob == true) {
                Job jobToEdit = new Job(JobListController.jobToEdit.getJobID(), jobName, status, notes, null, null, lastEditBy, installDate, address, city, zipCode, clientID, salesmanID, pmID);
                if (JobListController.jobToEdit.getProgress().equals("Completed")) {
                    Alerts.errorAlerts(15);
                } else {
                    System.out.println(1);
                    if (currentStatusComboBox.getValue().equals("Completed")) {
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setTitle("Confirmation");
                        a.setHeaderText("Once a job is complete, you can no longer edit it.");
                        a.setContentText("Are you sure you would like to continue");
                        a.showAndWait();
                        System.out.println(2);
                        if (a.getResult() == ButtonType.OK) {
                            DBJobs.dbUpdateJob(jobToEdit);
                            Alerts.informationAlerts(6);
                            addProductButton.setDisable(true);
                            removeProductButton.setDisable(true);
                        }
                    } else {
                        DBJobs.dbUpdateJob(jobToEdit);
                        Alerts.informationAlerts(6);
                        System.out.println(4);
                    }
                }
            } else {
                System.out.println(5);
                if (currentStatusComboBox.getValue().equals("Completed")) {
                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Confirmation");
                    a.setHeaderText("Once a job is complete, you can no longer edit it.");
                    a.setContentText("Are you sure you would like to continue");
                    a.showAndWait();
                    if (a.getResult() == ButtonType.OK) {
                        DBJobs.dbAddJob(jobToAdd);
                        Alerts.informationAlerts(5);
                    }
                } else {
                    DBJobs.dbAddJob(jobToAdd);
                    Alerts.informationAlerts(5);
                }
            }
        } catch (Exception e) {
            Alerts.errorAlerts(10);
        }
    }

    @FXML
    private void addProductQuantityEnterPressed(ActionEvent event) {
        addProductButton.fire();
    }

    @FXML
    private void addProductButton(ActionEvent event) {
        if (currentJob == null) {
            Alerts.errorAlerts(16);
        } else {
            int quantityToAdd;
            try {
                if (addProductsTableQuantityTextField.getText().equals("")) {
                    quantityToAdd = 1;
                } else {
                    quantityToAdd = Integer.parseInt(addProductsTableQuantityTextField.getText());
                }
                Product productToAdd = addProductsTable.getSelectionModel().getSelectedItem();
                for (Job j : getAllJobs()) {
                    if (j.getJobName().equals(currentJobName)) {
                        currentJob = j;
                    }
                }
                int productID = productToAdd.getProductID();
                String modelNumber = productToAdd.getModelNumber();
                BigDecimal productPrice = productToAdd.getPrice();
                String productCategory = productToAdd.getCategory();
                String productBrand = productToAdd.getBrand();
                JobInvoice newInvoice = new JobInvoice(currentJob.getJobID(), productID, quantityToAdd, modelNumber, productPrice, productCategory, productBrand);
                DBJobInvoice.dbAddJobItem(newInvoice);
                productsAdded.add(newInvoice);
                productsToAdd.remove(productToAdd);
                addProductsTable.setItems(productsToAdd);
                productsSoldTable.setItems(productsAdded);
                addProductsTableQuantityTextField.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void removeProductQuantityEnterPressed(ActionEvent event) {
        removeProductButton.fire();
    }

    @FXML
    private void removeProductButton(ActionEvent event) {
        int quantityToRemove;
        if (productsSoldTableQuantityTextField.getText().equals("")) {
            quantityToRemove = 1;
        } else {
            quantityToRemove = Integer.parseInt(productsSoldTableQuantityTextField.getText());
        }
        JobInvoice currentlySelected = productsSoldTable.getSelectionModel().getSelectedItem();
        System.out.println(currentlySelected.getQuantity());
        try {
        if (quantityToRemove > currentlySelected.getQuantity()) {
            Alerts.errorAlerts(11);
        } else if (quantityToRemove == currentlySelected.getQuantity()) {
            Product productToAddBack = null;
            for (Product p : getAllProducts()) {
                if (p.getProductID() == currentlySelected.getProductID()) {
                    productToAddBack = p;
                }
            }
            dbDeleteJobItem(currentlySelected);
            productsToAdd.add(productToAddBack);
            productsAdded.remove(currentlySelected);
            addProductsTable.setItems(productsToAdd);
            productsSoldTable.setItems(productsAdded);
        } else {
            productsAdded.remove(currentlySelected);
            int newQuantity = currentlySelected.getQuantity() - quantityToRemove;
            currentlySelected.setQuantity(newQuantity);
            dbUpdateJobItem(currentlySelected);
            productsAdded.add(currentlySelected);
            productsSoldTable.refresh();
        }
        productsSoldTableQuantityTextField.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelButton(ActionEvent event) throws IOException {
        Alert a = new Alert(CONFIRMATION);
        a.setHeaderText("Are you sure you would like to continue?");
        a.setContentText("This will remove any unsaved changes");
        a.showAndWait();
        if (a.getResult() == ButtonType.OK) {
            currentJob = null;
            JobListController.jobToEdit = null;
            Parent root = FXMLLoader.load(getClass().getResource("/view/JobList.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Jobs Menu");
            stage.show();
        }
    }
    
}
