package com.example.keuanganmahasiswa.controller;

import com.example.keuanganmahasiswa.DatabaseConnection;
import com.example.keuanganmahasiswa.MainApplication;
import com.example.keuanganmahasiswa.RuntimeConfiguration;
import com.example.keuanganmahasiswa.model.Transaksi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    DatabaseConnection db = new DatabaseConnection();
    Connection connection = db.getConnection();
    @FXML
    TextField nominal;
    @FXML
    ChoiceBox cbTransaksi,cbKategori;
    @FXML
    DatePicker dpTanggal;
    @FXML
    TableView<Transaksi> MyTable;
    @FXML
    TableColumn<Transaksi,Integer> colNo,colNominal;
    @FXML
    TableColumn<Transaksi,String> colTransaksi,colKategori,colTanggal;
    @FXML
    TableColumn<Transaksi,Void> colAction;
    ObservableList<Transaksi> mylist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cbTransaksi.setItems(FXCollections.observableArrayList(
                "Masuk","Keluar"
        ));
        cbKategori.setItems(FXCollections.observableArrayList(
                "Primer","Sekunder","Tersier"
        ));
        read();
    }

    private void read() {
        mylist.clear();
        String query = "select * from transaksi where user_id="+RuntimeConfiguration.getLoginId();
        try {
            Statement statement =  connection.createStatement();
            ResultSet output = statement.executeQuery(query);
            while (output.next()){
                mylist.add(new Transaksi(output.getInt("id"),output.getInt("user_id"),
                        output.getInt("nominal"),output.getString("kategori"),output.getString("tanggal"),output.getString("jenis_transaksi")));
            }
            colNo.setCellFactory(column -> new TableCell<>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (this.getTableRow() != null) {
                        int index = this.getTableRow().getIndex();
                        setText(empty ? "" : String.valueOf(index + 1));
                    }
                }
            });
            colNominal.setCellValueFactory(new PropertyValueFactory<>("nominal"));
            colTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
            colKategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));
            colTransaksi.setCellValueFactory(new PropertyValueFactory<>("jenis_transaksi"));
            colAction.setCellFactory(col -> {
                Button button = new Button("hapus");
                button.setStyle("-fx-background-color: #ff6666;");
                button.setTextFill(Color.WHITE);
                VBox vBox = new VBox(button);
                TableCell<Transaksi, Void> cell = new TableCell<Transaksi, Void>() {
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(vBox);
                        }
                    }
                };
                button.setOnAction(event -> {
                    Transaksi item = cell.getTableView().getItems().get(cell.getIndex());
                    delete(item.getId());
                });
                return cell;
            });
            MyTable.setItems(mylist);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void delete(int id) {
        String query = "delete from transaksi where id="+id;
        try {
            Statement statement = connection.createStatement();
            int res = statement.executeUpdate(query);
            if(res > 0){
                read();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tambah(ActionEvent event) {
        if((nominal.getText().equals("") && cbTransaksi.getSelectionModel().getSelectedItem().toString().equals(""))==false){

            String query = "INSERT INTO transaksi " +
                    "(user_id,nominal,kategori,tanggal,jenis_transaksi) " +
                    "values (?,?,?,?,?)";
            try{
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, Integer.parseInt(RuntimeConfiguration.getLoginId()));
                statement.setString(2,nominal.getText());
                statement.setString(3,cbKategori.getSelectionModel().getSelectedItem().toString());
                statement.setString(4,dpTanggal.getValue().toString());
                statement.setString(5,cbTransaksi.getSelectionModel().getSelectedItem().toString());
                statement.executeUpdate();
                nominal.setText("");
                cbTransaksi.getSelectionModel().clearSelection();
                read();
            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }
        }
    }

    public void logout(ActionEvent event) {
        try {
            Window mywindow = nominal.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = (Stage) mywindow;
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
            RuntimeConfiguration.saveLoginId("0");
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    public void toReport(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("report.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 495, 200);
            Stage stage = new Stage();
            stage.setTitle("report");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
