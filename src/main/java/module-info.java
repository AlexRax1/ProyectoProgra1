module com.mycompany.projectoprogra1fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    opens com.mycompany.projectoprogra1fx to javafx.fxml;
    exports com.mycompany.projectoprogra1fx;
}
