module com.example.miniproyecto2pruebas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.miniproyecto2pruebas to javafx.fxml;
    exports com.example.miniproyecto2pruebas;
    opens com.example.miniproyecto2pruebas.model to javafx.fxml;
    exports com.example.miniproyecto2pruebas.model;
    exports com.example.miniproyecto2pruebas.controller;
    opens com.example.miniproyecto2pruebas.controller to javafx.fxml;
    exports com.example.miniproyecto2pruebas.view;
    opens com.example.miniproyecto2pruebas.view to javafx.fxml;
}