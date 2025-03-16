module com.project.wsbuilder {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;

    opens com.project.wsbuilder.controllers to javafx.fxml;
    exports com.project.wsbuilder;
    exports com.project.wsbuilder.controllers;
    exports com.project.wsbuilder.models;
}
