module com.example.quizconnect {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.quizconnect to javafx.fxml;
    opens com.example.quizconnect.controllers to javafx.fxml;

    exports com.example.quizconnect;
}