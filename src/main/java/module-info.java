module com.example.miniproyecto2pruebas {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.miniproyecto2pruebas to javafx.fxml;
    exports com.example.miniproyecto2pruebas;
}