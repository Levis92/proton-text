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

import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.io.IOException;

/**
 * @author Stina werme
 * Created by stinawerme on 20/05/17.
 *
 * Extends java class TreeCell to make the tree editable for the user.
 * The Treecell wraps around a treeItem and has methods to get the treeItem.
 */
public class EditableTreeCell extends TreeCell<File> {

    private TextField textField;
    private ContextMenu contextMenu;


    public EditableTreeCell() {

    }

    /**
     * Start edit
     */
    @Override
    public void startEdit() {
        super.startEdit();

        if (textField == null) {
            createTextField();
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
    }

    /**
     * Cancel edit
     */
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        File item = getItem();

        item.getName();
        setText(item.getName());
        setGraphic(getTreeItem().getGraphic());
    }

    /**
     * Update item
     * @param item
     * @param empty
     */
    @Override
    public void updateItem(File item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
            return;
        }

        if (isEditing()) {
            if (textField != null) {
                textField.setText(getString());
            }

            setText(null);
            setGraphic(textField);
            return;
        }

        setText(getString());
        setGraphic(getTreeItem().getGraphic());
        createContextMenu();
    }

    /**
     * Create a contextMenu with varying items depending on the type of treeItem.
     */
    private void createContextMenu() {
        TreeItem<File> treeItem = getTreeItem();
        contextMenu = new ContextMenu();

        if (treeItem.getParent() == null) {
            createAddFileMenuItem();
            createAddFolderMenuItem();
        }

        if (treeItem.getParent() != null && treeItem.getValue().isDirectory()) {
            createAddFileMenuItem();
            createAddFolderMenuItem();
            createDeleteMenuItem();
        }

        setContextMenu(contextMenu);
    }

    /**
     * Create a context menu item for adding files.
     */
    private void createAddFileMenuItem() {
        MenuItem addMenuItem = new MenuItem("New file");
        contextMenu.getItems().add(addMenuItem);
        addMenuItem.setOnAction(t -> {

            TreeItem<File> currentTreeItem = getTreeItem();
            File currentFile = currentTreeItem.getValue();

            String path = null;
            try {
                path = currentFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }

            File f = new File(path, "untitled.md");

            f.getParentFile().mkdirs();
            int number = 2;

            try {
                while (f.exists()) {
                    f = new File(path, "untitled" + number + ".md");
                    number++;
                }
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            TreeItem newTreeItem = new TreeItem<File>();
            newTreeItem.setValue(f);
            currentTreeItem.getChildren().add(newTreeItem);

            if (!currentTreeItem.isExpanded()) {
                currentTreeItem.setExpanded(true);
            }
        });
    }

    /**
     * Create a context menu item for adding folders.
     */
    private void createAddFolderMenuItem() {
        MenuItem addMenuItem = new MenuItem("New folder");
        contextMenu.getItems().add(addMenuItem);
        addMenuItem.setOnAction(t -> {

            TreeItem<File> currentTreeItem = getTreeItem();
            File currentFile = currentTreeItem.getValue();

            String path = null;
            try {
                path = currentFile.getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }

            File f = new File(path, "untitledFolder");

            f.getParentFile().mkdirs();
            int number = 2;

            while (f.exists()) {
                f = new File(path, "untitledFolder" + number);
                number++;
            }
            f.mkdir();

            TreeItem newTreeItem = new TreeItem<File>();
            newTreeItem.setValue(f);
            currentTreeItem.getChildren().add(newTreeItem);

            if (!currentTreeItem.isExpanded()) {
                currentTreeItem.setExpanded(true);
            }
        });
    }

    /**
     * Create a context menu item for deleting files.
     */
    private void createDeleteMenuItem() {
        MenuItem deleteMenuItem = new MenuItem("Delete");
        contextMenu.getItems().add(deleteMenuItem);
        deleteMenuItem.setOnAction(t -> {
            TreeItem<File> currentTreeItem = getTreeItem();
            File currentFile = currentTreeItem.getValue();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Delete " + currentFile.getName() + " ?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CANCEL) {
                return;
            }
            currentTreeItem.getParent().getChildren().remove(currentTreeItem);
            currentFile.delete();
        });
    }

    /**
     * Create a text field to change name
     * If user clicks enter, update name
     */
    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased(keyReleaseEventObject -> {
            if (keyReleaseEventObject.getCode() == KeyCode.ENTER) {
                String name = textField.getText();
                File newFileName = new File(getItem().getParentFile(), name);
                getItem().renameTo(newFileName);
                commitEdit(newFileName);

            } else if (keyReleaseEventObject.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().getName();
    }
}
