module com.example.keuanganmahasiswa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires mysql.connector.j;

    opens com.example.keuanganmahasiswa to javafx.fxml;
    opens  com.example.keuanganmahasiswa.model to javafx.base;
    exports com.example.keuanganmahasiswa;
    exports com.example.keuanganmahasiswa.controller;
    opens com.example.keuanganmahasiswa.controller to javafx.fxml;
}