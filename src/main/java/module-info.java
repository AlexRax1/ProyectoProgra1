module com.mycompany.projectoprogra1fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Abre el paquete Modelo a javafx.fxml para permitir acceso desde FXML
    opens Modelo to javafx.fxml, javafx.base;

    // Exporta el paquete Modelo si necesitas acceso desde otros módulos
    exports Modelo;

    // Abre el paquete com.mycompany.projectoprogra1fx a javafx.fxml para acceso desde FXML
    opens com.mycompany.projectoprogra1fx to javafx.fxml;

    // Exporta el paquete com.mycompany.projectoprogra1fx para acceso desde otros módulos
    exports com.mycompany.projectoprogra1fx;
}
