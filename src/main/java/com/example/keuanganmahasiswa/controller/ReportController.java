package com.example.keuanganmahasiswa.controller;

import com.example.keuanganmahasiswa.DatabaseConnection;
import com.example.keuanganmahasiswa.RuntimeConfiguration;
import com.example.keuanganmahasiswa.model.Transaksi;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ReportController implements Initializable {
    DatabaseConnection db = new DatabaseConnection();
    Connection connection = db.getConnection();
    @FXML
    TextField masuk,keluar,total,keluarpersen;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        count();
    }

    private void count() {
        String query = "select * from transaksi where user_id="+ RuntimeConfiguration.getLoginId();
        int masukCount = 0;
        int keluarCount = 0;
        try {
            Statement statement =  connection.createStatement();
            ResultSet output = statement.executeQuery(query);
            while (output.next()){
                if(output.getString("jenis_transaksi").equals("Masuk")){
                    masukCount += output.getInt("nominal");
                }else{
                    keluarCount += output.getInt("nominal");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        float d = (float) keluarCount/(float)masukCount*100;
        masuk.setText(String.valueOf(masukCount));
        keluar.setText(String.valueOf(keluarCount));
        keluarpersen.setText(String.valueOf(d)+"%");
        total.setText(String.valueOf(masukCount-keluarCount));
    }


}
