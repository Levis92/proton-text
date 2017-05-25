package edu.chl.proton.control;

import edu.chl.proton.model.IFileHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;

/**
 * @Author Stina Werme
 * Created by stinawerme on 24/05/17.
 */
public class FileTree {

    private TreeView<File> treeView;

    public FileTree(TreeView<File> treeView, IFileHandler file) {
        this.treeView = treeView;
        File currentDir = new File(file.getCurrentDirectory().getPath()); // current directory
        populateTree(currentDir, null);

        treeView.setEditable(true);
        treeView.setShowRoot(false);
        treeView.setCellFactory(p -> new EditableTreeCell());
    }

    public void populateTree(File dir, TreeItem<File> parent) {
        TreeItem root = new TreeItem<>(dir);

        root.setValue(dir);

        if (parent == null) {
            root.setExpanded(true);

        } else {
            root.setExpanded(false);
        }
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                populateTree(file, root);

            } else {
                TreeItem item = new TreeItem<>(file);
                item.setValue(file);
                root.getChildren().add(item);
            }
        }
        if(parent == null){
            treeView.setRoot(root);

        } else {
            parent.getChildren().add(root);
        }
    }
}
