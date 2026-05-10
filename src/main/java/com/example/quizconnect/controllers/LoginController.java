package com.example.quizconnect.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginController {

    @FXML private Button loginTabBtn, registerTabBtn, submitBtn;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ToggleButton studentBtn, teacherBtn;
    @FXML private VBox formBox;
    @FXML private HBox roleBox;

    private boolean isLoginMode = true;

    // Extra fields for register mode (created dynamically)
    private Label emailLabel;
    private TextField emailField;
    private Label confirmLabel;
    private PasswordField confirmField;

    @FXML
    public void initialize() {
        // Group the role toggle buttons so only one can be selected at a time
        ToggleGroup roleGroup = new ToggleGroup();
        studentBtn.setToggleGroup(roleGroup);
        teacherBtn.setToggleGroup(roleGroup);

        // Update visual "selected" style when toggled
        roleGroup.selectedToggleProperty().addListener((obs, oldV, newV) -> {
            studentBtn.getStyleClass().remove("role-selected");
            teacherBtn.getStyleClass().remove("role-selected");
            if (newV != null) {
                ((ToggleButton) newV).getStyleClass().add("role-selected");
            }
        });

        // Pre-create the register-only fields (added/removed when toggling)
        emailLabel = new Label("Email");
        emailLabel.getStyleClass().add("field-label");

        emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.getStyleClass().add("input-field");

        confirmLabel = new Label("Confirm Password");
        confirmLabel.getStyleClass().add("field-label");

        confirmField = new PasswordField();
        confirmField.setPromptText("Confirm your password");
        confirmField.getStyleClass().add("input-field");
    }

    @FXML
    private void handleShowLogin() {
        if (isLoginMode) return;
        isLoginMode = true;

        loginTabBtn.getStyleClass().add("tab-active");
        registerTabBtn.getStyleClass().remove("tab-active");

        submitBtn.setText("Sign In");

        // Remove register-only fields
        formBox.getChildren().removeAll(emailLabel, emailField, confirmLabel, confirmField);
    }

    @FXML
    private void handleShowRegister() {
        if (!isLoginMode) return;
        isLoginMode = false;

        registerTabBtn.getStyleClass().add("tab-active");
        loginTabBtn.getStyleClass().remove("tab-active");

        submitBtn.setText("Create Account");

        // Insert Email fields after Username field (index 2)
        formBox.getChildren().add(2, emailLabel);
        formBox.getChildren().add(3, emailField);

        // Insert Confirm Password fields after Password field
        formBox.getChildren().add(8, confirmLabel);
        formBox.getChildren().add(9, confirmField);
    }

    @FXML
    private void handleSubmit() {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        String role = studentBtn.isSelected() ? "Student" : "Teacher";

        if (isLoginMode) {
            System.out.println("LOGIN → user: " + user + " | role: " + role);
            // TODO: validate then navigate
            // SceneManager.switchTo("/views/Dashboard.fxml", "/styles/dashboard.css", "QuizConnect — Dashboard");
        } else {
            String email = emailField.getText();
            String confirm = confirmField.getText();

            if (!pass.equals(confirm)) {
                System.out.println(" Passwords do not match!");
                return;
            }

            System.out.println("REGISTER → user: " + user +
                    " | email: " + email + " | role: " + role);
            // TODO: save to DB, then navigate
        }
    }
}