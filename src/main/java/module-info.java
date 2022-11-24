module com.example.milestone3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.milestone3 to javafx.fxml;
    exports com.example.milestone3;
}