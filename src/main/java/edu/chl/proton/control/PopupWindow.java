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
 * @Author Stina Werme
 * Created by stinawerme on 24/05/17.
 *
 * Open popup window when user tries to close the application
 */
public class PopupWindow {

    private boolean clicked = false;

    public PopupWindow(Window owner, String title, String text) {
        addPrompt(owner, title, text);
    }

    private void addPrompt(Window owner, String title, String text) {
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


        final JFXButton noButton = new JFXButton("No");
        noButton.setDefaultButton(true);
        noButton.setOnAction(t -> {
            dialog.close();
        });

        final JFXButton yesButton = new JFXButton("Yes");
        yesButton.setDefaultButton(true);
        yesButton.setOnAction(t -> {
            clicked();
            dialog.close();
        });

        noButton.getStylesheets().add("edu/chl/proton/view/style.css");
        noButton.getStyleClass().add("button");

        yesButton.getStylesheets().add("edu/chl/proton/view/style.css");
        yesButton.getStyleClass().add("button");

        final VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER_RIGHT);
        layout.setStyle("-fx-background-color: azure; -fx-padding: 10;");
        layout.getChildren().setAll(
                message,
                noButton,
                yesButton
        );

        dialog.setScene(new Scene(layout));
        dialog.showAndWait();

    }

    private void clicked() {
        clicked = true;
    }

    public boolean resultIsYes() {
        return clicked;
    }
}
