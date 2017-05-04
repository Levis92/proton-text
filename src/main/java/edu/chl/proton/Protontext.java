package edu.chl.proton;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Anton Levholm
 */

public class Protontext extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/chl/proton/view/main.fxml"));
        primaryStage.setTitle("Proton Text");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
