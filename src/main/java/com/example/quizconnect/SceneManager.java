package com.example.quizconnect;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static final String BASE = "/com/example/quizconnect";
    private static Stage primaryStage;

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static void switchTo(String fxmlPath, String cssPath, String title) {
        try {
            Parent root = FXMLLoader.load(SceneManager.class.getResource(BASE + fxmlPath));
            Scene scene = new Scene(root);

            scene.getStylesheets().add(
                    SceneManager.class.getResource(BASE + "/styles/global.css").toExternalForm()
            );

            if (cssPath != null) {
                scene.getStylesheets().add(
                        SceneManager.class.getResource(BASE + cssPath).toExternalForm()
                );
            }

            primaryStage.setScene(scene);
            primaryStage.setTitle(title);

        } catch (Exception e) {
            System.err.println("❌ Failed to load: " + fxmlPath);
            e.printStackTrace();
        }
    }

    public static Stage getStage() {
        return primaryStage;
    }
}