package edu.chl.proton.control;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.io.File;

/**
 * @author Stina werme
 * Created by stinawerme on 20/05/17.
 *
 * Extends java class TreeCell to make the tree editable for the user.
 */
final class EditableTreeCell extends TreeCell<File> {

    private TextField textField;
    private ContextMenu addMenu = new ContextMenu();

    public EditableTreeCell() {

        MenuItem addMenuItem = new MenuItem("Add File");
        addMenu.getItems().add(addMenuItem);
        addMenuItem.setOnAction(new EventHandler() {
            public void handle(Event t) {
                TreeItem newFile =
                        new TreeItem<File>();
                getTreeItem().getChildren().add(newFile);
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
