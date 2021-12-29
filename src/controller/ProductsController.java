/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static dao.DBJobInvoice.dbDeleteJobItem;
import static dao.DBJobInvoice.getAllJobItems;
import static dao.DBProducts.dbAddProduct;
import static dao.DBProducts.dbDeleteProduct;
import static dao.DBProducts.dbUpdateProduct;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static dao.DBProducts.getAllProducts;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import model.JobInvoice;
import model.Product;
import utils.Alerts;

/**
 * FXML Controller class
 *
 * @author indya
 */
public class ProductsController implements Initializable {

    @FXML
    private TextField modelNumberTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private TextField brandTextField;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, String> modelNumberColumn;
    @FXML
    private TableColumn<Product, String> categoryColumn;
    @FXML
    private TableColumn<Product, String> brandColumn;
    @FXML
    private TableColumn<Product, BigDecimal> priceColumn;
    
    private ObservableList<String> categories = FXCollections.observableArrayList();
    private boolean modified = false;
    private Product productToModify;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modified = false;
        productsTable.setItems(getAllProducts());
        modelNumberColumn.setCellValueFactory(new PropertyValueFactory<>("modelNumber"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        categories.addAll("Audio Equipment", "Video Equipment", "Television", "Receiver", "Billable Part", "Other");
        categoryComboBox.setItems(categories);
    }    

    @FXML
    private void addButton(ActionEvent event) throws SQLException {
        try {
            String modelNumberInput = modelNumberTextField.getText();
            String brandInput = brandTextField.getText();
            String categoryInput = categoryComboBox.getValue();
            BigDecimal priceInput = new BigDecimal(priceTextField.getText());
            if (modified == false) {
                Product productToAdd = new Product(modelNumberInput, priceInput, categoryInput, brandInput);
                dbAddProduct(productToAdd);
                productsTable.setItems(getAllProducts());
                modified = true;
                clearAllFields();
            } else {
                Product productToAdd = new Product(productToModify.getProductID(), modelNumberInput, priceInput, brandInput, categoryInput);
                dbUpdateProduct(productToAdd);
                productsTable.setItems(getAllProducts());
                modified = false;
                clearAllFields();
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            Alerts.errorAlerts(2);
        }
    }

    @FXML
    private void editButton(ActionEvent event) {
        productToModify = productsTable.getSelectionModel().getSelectedItem();
        if (productToModify == null) {
            Alerts.errorAlerts(3);
        } else {
            modelNumberTextField.setText(productToModify.getModelNumber());
            priceTextField.setText(productToModify.getPrice().toString());
            brandTextField.setText(productToModify.getBrand());
            categoryComboBox.setValue(productToModify.getCategory());
            modified = true;
        }
    }

    @FXML
    private void deleteButton(ActionEvent event) throws SQLException {
        Product productToDelete = productsTable.getSelectionModel().getSelectedItem();
        if (productToDelete == null) {
            Alerts.errorAlerts(8);
        } else {
            for (JobInvoice ji : getAllJobItems()) {
                if (ji.getProductID() == productToDelete.getProductID()) {
                    dbDeleteJobItem(ji);
                }
            }
            dbDeleteProduct(productToDelete);
            clearAllFields();
            productsTable.setItems(getAllProducts());
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
    
    private void clearAllFields() {
        modelNumberTextField.clear();
        priceTextField.clear();
        brandTextField.clear();
        categoryComboBox.itemsProperty().setValue(null);
        categoryComboBox.setItems(categories);
        modified = false;
        productToModify = null;
        productsTable.setItems(getAllProducts());
        searchTextField.clear();
    }

    @FXML
    private void enterPressed(ActionEvent event) {
        searchButton.fire();
    }

    @FXML
    private void searchButton(ActionEvent event) {
        String keyword = searchTextField.getText();
        if (keyword == "") {
            productsTable.setItems(getAllProducts());
        } else {
            ObservableList<Product> searchResults = FXCollections.observableArrayList();
            for (Product p : getAllProducts()) {
                if (p.getModelNumber().contains(keyword)) {
                    searchResults.add(p);
                }
            }
            productsTable.setItems(searchResults);
        }
    }
    
}
