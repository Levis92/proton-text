package edu.chl.proton.control;

import edu.chl.proton.model.FileUtility;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.Event;
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
 */
public class EditableTreeCell extends TreeCell<File> {

    private TextField textField;
    private ContextMenu addMenu = new ContextMenu();
    private TreeView treeView= new TreeView();


    public EditableTreeCell() {

        MenuItem addMenuItem = new MenuItem("Add new");
        addMenu.getItems().add(addMenuItem);
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
                    f = new File(path, "untitled" + number +".md");
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

        MenuItem deleteMenuItem = new MenuItem("Delete");
        addMenu.getItems().add(deleteMenuItem);
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {

                // TreeItem item = (TreeItem)treeView.getSelectionModel().getSelectedItem();
                // item.getParent().getChildren().remove(item);
            }
        });

    }

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

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        File item = getItem();

        item.getName();
        setText(item.getName());
        setGraphic(getTreeItem().getGraphic());
    }

    @Override
    public void updateItem(File item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(getTreeItem().getGraphic());
                if (!getTreeItem().isLeaf()&&getTreeItem().getParent()!= null) {
                    setContextMenu(addMenu);
                }
            }
        }
    }

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
