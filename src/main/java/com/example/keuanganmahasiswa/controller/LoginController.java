package com.example.keuanganmahasiswa.controller;

import com.example.keuanganmahasiswa.DatabaseConnection;
import com.example.keuanganmahasiswa.MainApplication;
import com.example.keuanganmahasiswa.RuntimeConfiguration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField pfPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void toRegister(ActionEvent event) {
        try {
            Window mywindow = tfUsername.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("register.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = (Stage) mywindow;
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    public void login(ActionEvent event) {
        if ((tfUsername.getText().equals("") && pfPassword.getText().equals("")) == false){
            DatabaseConnection db = new DatabaseConnection();
            Connection connection = db.getConnection();
            String query = "SELECT * FROM users WHERE username = '" + tfUsername.getText() + "' AND password = '" + pfPassword.getText() + "'";
            try {
                Statement statement = connection.createStatement();
                ResultSet queryResult = statement.executeQuery(query);
                if (queryResult.next()) {
                    RuntimeConfiguration.saveLoginId(queryResult.getString("id"));
                    try {
                        Window mywindow = tfUsername.getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 768, 411);
                        Stage stage = (Stage) mywindow;
                        stage.setTitle("Main");
                        stage.setScene(scene);
                        stage.show();
                    } catch ( IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            tfUsername.setText("");
            pfPassword.setText("");
        }
    }
}