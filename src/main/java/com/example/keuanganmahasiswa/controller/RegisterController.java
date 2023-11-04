package com.example.keuanganmahasiswa.controller;

import com.example.keuanganmahasiswa.DatabaseConnection;
import com.example.keuanganmahasiswa.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    @FXML
    private PasswordField pfPassword;
    @FXML
    private TextField tfNIM,tfNama,tfUsername;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void register(ActionEvent event) {
        DatabaseConnection db = new DatabaseConnection();
        Connection connection = db.getConnection();
        String query = "INSERT INTO users " +
                "(nim,username,nama,password) " +
                "values (?,?,?,?)";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,tfNIM.getText());
            statement.setString(2,tfUsername.getText());
            statement.setString(3,tfNama.getText());
            statement.setString(4,pfPassword.getText());
            statement.executeUpdate();
            tfNama.setText("");
            tfUsername.setText("");
            pfPassword.setText("");
            tfNIM.setText("");
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void toLogin(ActionEvent event) {
        try {
            Window mywindow = tfUsername.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = (Stage) mywindow;
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }
}
