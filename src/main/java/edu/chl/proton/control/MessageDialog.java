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
