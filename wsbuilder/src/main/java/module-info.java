module com.project.wsbuilder {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.project.wsbuilder to javafx.fxml;
    exports com.project.wsbuilder;
}
