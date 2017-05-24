package edu.chl.proton.model;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-23.
 */
public class TextPrompt {
    private String result;
    private boolean clicked = false;

    public TextPrompt(Window owner, String title, String input) {
        addPrompt(owner, title, input);
    }

    public TextPrompt(Window owner, String title) {
        addPrompt(owner, title, "");
    }

    private void addPrompt(Window owner, String title, String input) {
        final Stage dialog = new Stage();

        dialog.setTitle(title);
        dialog.initOwner(owner);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setWidth(350.0);
        dialog.setX(owner.getX() + owner.getWidth()/2 - dialog.getWidth()/2);
        dialog.setY(owner.getY() + 220);

        final JFXTextField textField = new JFXTextField();
        final JFXButton submitButton = new JFXButton("Submit");
        submitButton.setDefaultButton(true);
        submitButton.setOnAction(t -> {
            clicked();
            dialog.close();
        });
        submitButton.getStylesheets().add("edu/chl/proton/view/style.css");
        submitButton.getStyleClass().add("button");
        textField.setMinHeight(JFXTextField.USE_PREF_SIZE);
        textField.insertText(0, input);

        final VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER_RIGHT);
        layout.setStyle("-fx-background-color: azure; -fx-padding: 10;");
        layout.getChildren().setAll(
                textField,
                submitButton
        );

        dialog.setScene(new Scene(layout));
        dialog.showAndWait();

        if (clicked) result = textField.getText();
        else result = null;
    }

    private void clicked() {
        clicked = true;
    }

    public String getResult() {
        return result;
    }
}