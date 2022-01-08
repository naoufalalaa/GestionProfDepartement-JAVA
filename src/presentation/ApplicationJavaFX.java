package presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ApplicationJavaFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root= FXMLLoader.load(getClass().getResource("HomeView.fxml"));
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gestion École");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
