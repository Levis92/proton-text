/*
 * Proton Text - A Markdown text editor
 * Copyright (C) 2017  Anton Levholm, Ludvig Ekman, Mickaela Södergren
 * and Stina Werme
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.chl.proton.view.popup;

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
        addPrompt(owner, title, null);
    }

    private void addPrompt(Window owner, String title, String input) {
        final Stage dialog = new Stage();

        dialog.setTitle(title);
        dialog.initOwner(owner);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setWidth(350.0);
        dialog.setResizable(false);
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
        if (input != null) textField.insertText(0, input);

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