/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avcompanycrmapp;

import dao.DBConnection;
import java.sql.Connection;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author indya
 */
public class AVCompanyCRMApp extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        DBConnection.startConnection();
        Connection conn = DBConnection.getConnection();
        // TODO code application logic here
        launch(args);
        DBConnection.closeConnection();
        
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }
    
}
