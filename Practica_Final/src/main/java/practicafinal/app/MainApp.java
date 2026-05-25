package practicafinal.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Practica Final MP EEDD");
        primaryStage.setScene(new Scene(new StackPane(new Label("Proyecto base JavaFX")), 640, 480));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
