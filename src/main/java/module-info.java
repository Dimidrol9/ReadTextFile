module com.example.readtextfile {
    requires javafx.controls;
    requires javafx.fxml;
    requires freetts;


    opens com.example.readtextfile to javafx.fxml;
    exports com.example.readtextfile;
}