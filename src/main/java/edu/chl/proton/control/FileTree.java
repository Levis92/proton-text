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

import edu.chl.proton.model.workspace.IFileHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.io.IOException;

/**
 * @Author Stina Werme
 * Created by stinawerme on 24/05/17.
 *
 * Create file tree for the sidebar
 */
public class FileTree {

    private TreeView<File> treeView;

    public FileTree(TreeView<File> treeView, IFileHandler file) {
        this.treeView = treeView;
        File currentDir = new File(file.getCurrentDirectory().getPath()); // current directory
        populateTree(currentDir, null);

        treeView.setEditable(true);
        //treeView.setShowRoot(false);
        treeView.setCellFactory(p -> {
            try {
                return new EditableTreeCell();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    /**
     * Create file tree
     * @param dir
     * @param parent
     */
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
