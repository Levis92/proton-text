package edu.chl.proton.control;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * @Author Ludvig Ekman
 * Created by luddevig on 26/05/17.
 *
 * Open dialog window
 */
public class MessageDialog {

    public MessageDialog(Window owner, String title, String text) {
        addDialog(owner, title, text);
    }

    private void addDialog(Window owner, String title, String text) {
        final Stage dialog = new Stage();

        dialog.setTitle(title);
        dialog.initOwner(owner);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setWidth(250.0);
        dialog.setResizable(false);
        dialog.setX(owner.getX() + owner.getWidth() / 2 - dialog.getWidth() / 2);
        dialog.setY(owner.getY() + 220);

        final Text message = new Text();
        message.setText(text);
        message.setWrappingWidth(220.0);

        final JFXButton okButton = new JFXButton("Ok");
        okButton.setDefaultButton(true);
        okButton.setOnAction(t -> dialog.close());

        okButton.getStylesheets().add("edu/chl/proton/view/style.css");
        okButton.getStyleClass().add("button");

        final VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER_RIGHT);
        layout.setStyle("-fx-background-color: azure; -fx-padding: 10;");
        layout.getChildren().setAll(
                message,
                okButton
        );

        dialog.setScene(new Scene(layout));
        dialog.showAndWait();

    }
}
