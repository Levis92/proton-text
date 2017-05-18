package edu.chl.proton.control;

import com.jfoenix.controls.JFXTabPane;
import edu.chl.proton.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.text.Text;

import java.io.File;

import java.io.IOException;


/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 */
public class MainController {
    private static IFileHandler file;
    private static IDocumentHandler document;
    private FXMLLoader loader;

    @FXML
    private JFXTabPane tabPane;
    @FXML
    private TreeView<File> treeView;


    public void initialize() throws IOException {
        WorkspaceFactory factory = new WorkspaceFactory();
        file = factory.getWorkspace();
        document = factory.getWorkspace();
        loader = new FXMLLoader(getClass().getResource("/edu/chl/proton/view/markdown-tab.fxml"));
        Tab tab = new Tab("Untitled");
        tab.setContent(loader.load());
        tabPane.getTabs().add(tab);

        File currentDir = new File(file.getCurrentDirectory()); // current directory
        findFiles(currentDir, null);
    }


    private void findFiles(File dir, TreeItem<File> parent) {
        TreeItem root = new TreeItem<>(dir);
        root.setValue(dir.getName());
        if (parent == null) {
            root.setExpanded(true);
        } else {
            root.setExpanded(false);
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                findFiles(file, root);
            } else {
                TreeItem item = new TreeItem<>(file);
                item.setValue(file.getName());
                root.getChildren().add(item);
            }

        }
        if(parent == null){
            treeView.setRoot(root);
        } else {
            parent.getChildren().add(root);
        }
    }




    public void addNewTab(String name) throws IOException {
        Tab tab = new Tab(name);
        tab.setContent(loader.load());
        tabPane.getTabs().add(tab);
    }

    @FXML
    public void onClickNewButton(ActionEvent event) throws IOException {
        document.createDocument(DocumentType.MARKDOWN);
        addNewTab("Untitled.md");
    }

    @FXML
    public void onClickOpenButton(ActionEvent event) throws IOException {
        String file = "";
        document.openDocument(file);
    }

    @FXML
    public void onClickSaveButton(ActionEvent event) throws IOException {
        file.saveCurrentDocument();
    }

    @FXML
    public void onClickUndoButton(ActionEvent event) throws IOException {
        // Can be really hard to implement.
    }

    @FXML
    public void onClickRedoButton(ActionEvent event) throws IOException {

    }
}
