package com.example.quizconnect.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class LoginController {

    // ——— Tab buttons ———
    @FXML private Button loginTabBtn;
    @FXML private Button registerTabBtn;

    // ——— Always-visible fields ———
    @FXML private TextField     usernameField;
    @FXML private PasswordField passwordField;

    // ——— Register-only field groups ———
    // These are VBox wrappers — we toggle managed+visible on the VBox
    // so the label AND input both appear/disappear together with no gap
    @FXML private VBox          emailGroup;
    @FXML private TextField     emailField;
    @FXML private VBox          confirmPasswordGroup;
    @FXML private PasswordField confirmPasswordField;

    // ——— Role toggles ———
    @FXML private ToggleButton studentBtn;
    @FXML private ToggleButton teacherBtn;

    // ——— Feedback & submit ———
    @FXML private Label  errorLabel;
    @FXML private Button submitBtn;

    // ——— State ———
    private boolean isLoginMode = true;
    private ToggleGroup roleGroup;

    // ─────────────────────────────────────────────────────
    //  INITIALIZE
    // ─────────────────────────────────────────────────────

    @FXML
    public void initialize() {
        setupRoleToggleGroup();
    }

    private void setupRoleToggleGroup() {
        roleGroup = new ToggleGroup();
        studentBtn.setToggleGroup(roleGroup);
        teacherBtn.setToggleGroup(roleGroup);

        // ── Enforce: one must always be selected ──
        roleGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) {
                // User clicked the already-selected button → reselect it
                oldVal.setSelected(true);
                return;
            }
            refreshRoleStyles();
        });

        // Set initial visual state
        refreshRoleStyles();
    }

    // ─────────────────────────────────────────────────────
    //  TAB SWITCHING
    // ─────────────────────────────────────────────────────

    @FXML
    private void handleShowLogin() {
        if (isLoginMode) return;
        isLoginMode = true;

        // ── Swap tab styles ──
        loginTabBtn.getStyleClass().add("tab-active");
        registerTabBtn.getStyleClass().remove("tab-active");

        // ── Hide register-only groups ──
        setVisible(emailGroup,           false);
        setVisible(confirmPasswordGroup, false);

        // ── Update button text ──
        submitBtn.setText("Sign In");

        // ── Reset state ──
        clearFields();
        hideError();
    }

    @FXML
    private void handleShowRegister() {
        if (!isLoginMode) return;
        isLoginMode = false;

        // ── Swap tab styles ──
        registerTabBtn.getStyleClass().add("tab-active");
        loginTabBtn.getStyleClass().remove("tab-active");

        // ── Show register-only groups ──
        setVisible(emailGroup,           true);
        setVisible(confirmPasswordGroup, true);

        // ── Update button text ──
        submitBtn.setText("Create Account");

        // ── Reset state ──
        clearFields();
        hideError();
    }

    // ─────────────────────────────────────────────────────
    //  SUBMIT
    // ─────────────────────────────────────────────────────

    @FXML
    private void handleSubmit() {
        hideError();

        if (isLoginMode) {
            handleLogin();
        } else {
            handleRegister();
        }
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String role     = getSelectedRole();

        // ── Validation ──
        if (username.isEmpty()) {
            showError("Username cannot be empty.");
            usernameField.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            showError("Password cannot be empty.");
            passwordField.requestFocus();
            return;
        }

        // ── TODO Week 1: Wire to server ──
        // String json = JsonUtil.buildLoginRequest(username, password, role);
        // ClientConnection.getInstance().send(json);
        System.out.println("LOGIN → user:" + username + " | role:" + role);
    }

    private void handleRegister() {
        String username = usernameField.getText().trim();
        String email    = emailField.getText().trim();
        String password = passwordField.getText();
        String confirm  = confirmPasswordField.getText();
        String role     = getSelectedRole();

        // ── Validation — check each field in order ──
        if (username.isEmpty()) {
            showError("Username cannot be empty.");
            usernameField.requestFocus();
            return;
        }
        if (username.length() < 3) {
            showError("Username must be at least 3 characters.");
            usernameField.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            showError("Email cannot be empty.");
            emailField.requestFocus();
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            showError("Please enter a valid email address.");
            emailField.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            showError("Password cannot be empty.");
            passwordField.requestFocus();
            return;
        }
        if (password.length() < 6) {
            showError("Password must be at least 6 characters.");
            passwordField.requestFocus();
            return;
        }
        if (!password.equals(confirm)) {
            showError("Passwords do not match.");
            confirmPasswordField.clear();
            confirmPasswordField.requestFocus();
            return;
        }

        // ── TODO Week 1: Wire to server ──
        // String json = JsonUtil.buildRegisterRequest(username, email, password, role);
        // ClientConnection.getInstance().send(json);
        System.out.println("REGISTER → user:" + username
                + " | email:" + email + " | role:" + role);
    }

    // ─────────────────────────────────────────────────────
    //  HELPERS
    // ─────────────────────────────────────────────────────

    /**
     * Refreshes the CSS classes on role buttons
     * to match which one is currently selected.
     */
    private void refreshRoleStyles() {
        if (studentBtn.isSelected()) {
            addStyle(studentBtn, "role-selected");
            removeStyle(teacherBtn, "role-selected");
        } else {
            addStyle(teacherBtn, "role-selected");
            removeStyle(studentBtn, "role-selected");
        }
    }

    /**
     * Toggles visibility AND layout space together.
     * visible=false alone leaves a blank gap.
     * managed=false removes the node from layout flow completely.
     */
    private void setVisible(javafx.scene.Node node, boolean show) {
        node.setVisible(show);
        node.setManaged(show);
    }

    private void showError(String message) {
        errorLabel.setText(message);
        setVisible(errorLabel, true);
    }

    private void hideError() {
        errorLabel.setText("");
        setVisible(errorLabel, false);
    }

    private void clearFields() {
        usernameField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

    private String getSelectedRole() {
        return studentBtn.isSelected() ? "STUDENT" : "TEACHER";
    }

    // ── CSS class helpers (prevent duplicate class names) ──
    private void addStyle(javafx.scene.Node node, String style) {
        if (!node.getStyleClass().contains(style)) {
            node.getStyleClass().add(style);
        }
    }

    private void removeStyle(javafx.scene.Node node, String style) {
        node.getStyleClass().remove(style);
    }
}