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

import edu.chl.proton.model.documents.DocumentType;
import edu.chl.proton.model.workspace.IDocumentHandler;
import edu.chl.proton.model.workspace.IFileHandler;
import edu.chl.proton.model.workspace.IStageHandler;
import edu.chl.proton.model.workspace.WorkspaceFactory;
import edu.chl.proton.view.popup.MessageDialog;
import edu.chl.proton.view.popup.PopupWindow;
import edu.chl.proton.view.popup.TextPrompt;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.*;
import java.util.*;

import static java.lang.Boolean.TRUE;


/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 *
 * This class controls the functionality of the main GUI.
 */
public class MainController {
    private static IFileHandler file;
    private static IDocumentHandler document;
    private IStageHandler stage;
    private static SingleSelectionModel<Tab> selectionModel;
    private static Observable observable;
    private Observer observer;
    private FileTree fileTree;
    private static boolean isOpened = false;

    @FXML
    private TabPane tabPane;
    @FXML
    private TreeView<File> treeView;
    @FXML
    private AnchorPane treeViewPane;
    @FXML
    private SplitPane splitPane;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Text lastSaved;
    @FXML
    private Text filePath;


    public void initialize() throws IOException {
        WorkspaceFactory factory = new WorkspaceFactory();
        file = factory.getWorkspace();
        document = factory.getWorkspace();
        stage = factory.getWorkspace();
        observable = factory.getWorkspace();
        observer = new UpdateFooter(observable);
        filePath.setText("");
        lastSaved.setText("Not saved");
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        treeView.managedProperty().bind(treeView.visibleProperty());
        selectionModel = tabPane.getSelectionModel();
        menuBar.useSystemMenuBarProperty().set(true);
        addNewTab("Untitled.md");
        fileTree = new FileTree(treeView, file);

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            TreeItem<File> selectedItem = newValue;
            File file = new File(selectedItem.getValue().getPath());
            openFile(file);
        });
    }

    /**
     * Help method to open file
     * @param file
     */
    private void openFile(File file) {

        if (file != null && file.isFile() && !isAlreadyOpen(file)) {
            document.openDocument(file.getPath());
            try {
                addNewTab(file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try(BufferedReader br = new BufferedReader(new FileReader(file))) {
                List<String> lines = new ArrayList<>();
                String line;

                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
                isOpened = true;
                document.setText(lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isAlreadyOpen(File targetFile){
        int selected = file.isAlreadyOpen(targetFile);
        if(selected != -1) {
            selectionModel.select(selected);
            return true;
        }
        return false;
    }

    static boolean fileIsOpened() {
        return isOpened;
    }

    static void fileHasOpened() {
        isOpened = false;

    }

    /**
     * Adds a new tab to the TabPane and makes it the selected one.
     * @param name of the new tab.
     * @throws IOException if the FXML file cannot be found.
     */
    private void addNewTab(String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/chl/proton/view/markdown-tab.fxml"));
        Tab tab = new Tab(name);
        tab.getStyleClass().add("tab");
        tab.setContent(loader.load());
        selectionModel.select(tab);
        tabPane.getTabs().add(tab);
        tab.setOnSelectionChanged(e -> document.setCurrentDocument(selectionModel.getSelectedIndex()));
        tab.setOnCloseRequest(e -> document.removeDocument(tabPane.getTabs().indexOf(e.getTarget())));
        document.setCurrentDocument(selectionModel.getSelectedIndex());
    }

    /**
     * Change to the next tab when choosing "Next tab" in the menu bar.
     * @param event
     * @throws IOException
     */
    @FXML
    public void onClickNextTab(ActionEvent event) throws IOException {
        selectionModel.selectNext();
        document.setCurrentDocument(selectionModel.getSelectedIndex());
    }

    /**
     * Change to the previous tab when choosing "Previous tab" in the manu bar.
     * @throws IOException if there is no previous tab.
     */
    @FXML
    public void onClickPreviousTab() throws IOException {
        selectionModel.selectPrevious();
        document.setCurrentDocument(selectionModel.getSelectedIndex());
    }

    /**
     * Creates a new tab.
     * @throws IOException if the tab cannot be loaded.
     */
    @FXML
    public void onClickNewButton() throws IOException {
        document.createDocument(DocumentType.MARKDOWN);
        addNewTab("Untitled.md");
    }

    /**
     * Open the file manager when clicking on open in the menu bar.
     * Open the chosen file.
     * @throws IOException if the file cannot be opened.
     */
    @FXML
    public void onClickOpenButton() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File file = fileChooser.showOpenDialog(stage.getStage());
        openFile(file);
        document.getCurrentDocument().notifyObservers();
    }

    /**
     * Save current file when clicking on save in the menu bar.
     * @throws IOException if there is no directory.
     */
    @FXML
    public void onClickSaveButton() throws IOException {
        if (!file.saveCurrentDocument()) {
            String title = "Filepath";
            String input = file.getCurrentDirectory().getPath() + "/filename.md";
            TextPrompt prompt = new TextPrompt(stage.getStage(),title,input);
            if ((input = prompt.getResult()) != null) {
                file.saveCurrentDocument(input);
                fileTree.populateTree(file.getCurrentDirectory(), null);
            }
        }
    }

    @FXML
    public void onClickUndoButton(ActionEvent event) throws IOException {

    }

    @FXML
    public void onClickRedoButton(ActionEvent event) throws IOException {

    }

    /**
     * Changes the current directory and displays the new file tree.
     * @throws IOException if a file is not chosen.
     */
    @FXML
    public void onClickChangeDirectory() throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Change directory");
        File file = directoryChooser.showDialog(stage.getStage());
        if (file != null && file.isDirectory()) {
            MainController.file.setCurrentDirectory(file);
            File currentDir = new File(MainController.file.getCurrentDirectory().getPath());
            fileTree.populateTree(currentDir, null);
        }
    }

    /**
     * Toggle between showing and hiding the file tree view.
     * @throws IOException if elements cannot be located.
     */
    @FXML
    public void onClickToggleTreeViewVisibility() throws IOException {
        if (treeViewPane.isVisible()) {
            treeViewPane.setVisible(false);
            treeViewPane.setMaxWidth(0);
            treeViewPane.setMinWidth(0);
            splitPane.getStyleClass().add("hide");
        } else {
            treeViewPane.setVisible(true);
            treeViewPane.setMaxWidth(250);
            treeViewPane.setMinWidth(150);
            splitPane.getStyleClass().removeAll("hide");
        }
    }

    /**
     * When choosing rename file a window is opened and the user can then choose to change the name.
     * @throws IOException if not able to rename file.
     */
    public void onClickRenameFile() throws IOException {
        if (file.exists()) {
            String path = file.getPath();
            String title = "Set new name";
            TextPrompt prompt = new TextPrompt(stage.getStage(), title, path);
            try {
                String newName=checkCorrectFileName(prompt, title).getResult();
                if (newName!=null) {
                    file.saveCurrentDocument(newName);
                    File newer = new File(newName);
                    File older = new File(path);
                    older.renameTo(newer);
                    selectionModel.getSelectedItem().setText(newer.getName());
                    fileTree.populateTree(file.getCurrentDirectory(), null);
                }

            } catch (NullPointerException eNull) {
                System.err.println("Exited TextPromt without creating new file name");
            }

        } else {
            System.out.println("Does not find file.");

        }
    }

    /**
     * When the user clicks on "Save as" a window opens and the user can then save the file with the current name or change it.
     * @throws IOException if the file cannot be saved.
     */
    public void onClickSaveAs() throws IOException {
        String path ="./filename1.md";
        if (file.exists()){
            path = file.getPath();
        }
        String title = "Save file as";
        TextPrompt prompt = new TextPrompt(stage.getStage(),title,path);
        try {
            String newName=checkCorrectFileName(prompt, title).getResult();
            if (newName!=null) {
                file.saveCurrentDocument(newName);
            }
        } catch (NullPointerException eNull) {
            System.err.println("Exited TextPromt without choosing file");
        }
        fileTree.populateTree(file.getCurrentDirectory(), null);

    }

    /**
     * Check so that the file name is correct.
     * If wrong the user is told to write the name in the right format.
     * @param prompt is the object used for getting user input.
     * @param title is the title of the window to open.
     * @return the TextPrompt object with the result.
     */
    private TextPrompt checkCorrectFileName(TextPrompt prompt, String title) {
        int pLength = prompt.getResult().length();
        while ( (pLength <6)==TRUE  ||
                !((prompt.getResult()).substring(pLength-4).equals(".pdf") ||
                        (prompt.getResult()).substring(pLength-4).equals(".txt") ||
                        (prompt.getResult()).substring(pLength-3).equals(".md"))
                //|| !(prompt.getResult().substring(0,2).equals("./"))
                )
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Write a correct file name.");
            alert.showAndWait();
            prompt = new TextPrompt(stage.getStage(), title, prompt.getResult());
            pLength=prompt.getResult().length();
        }

        //TODO: fånga Nullpointer när man kryssar
        return prompt;

    }

    /**
     * Show popup window when trying to close the application
     */
    public void onClickCloseApplication() {
        String title = "Close application";
        String message = "Are you sure you want to quit Proton Text?";
        PopupWindow popup = new PopupWindow(stage.getStage(),title,message);
        if (popup.resultIsYes()) stage.getStage().close();
    }

    /**
     * Close the current tab.
     */
    @FXML
    public void onClickCloseCurrentTab() {
        int index = selectionModel.getSelectedIndex();
        tabPane.getTabs().remove(index);
        document.removeCurrentDocument();
        document.setCurrentDocument(index);
    }

    /**
     * Close all open tabs.
     */
    @FXML
    public void onClickCloseAllTabs() {
        document.removeAllDocuments();
        int count = tabPane.getTabs().size();
        tabPane.getTabs().remove(0, count);
        observable.deleteObservers();
        observable.addObserver(observer);
    }

    /**
     * Show information about the application when clicking on "about".
     */
    public void onClickAbout() {
        new MessageDialog(stage.getStage(),"About Proton Text","Proton Text is a " +
                "text editor created by students at Chalmers University of Technology. \n" +
                "\nAuthors: Ludvig Ekman, Anton Levholm, Mickaela Södergren and Stina Werme.\n" +
                "\nCourse: TDA367");
    }

    /**
     * Inner class that handles the updating of the footer bar. It updates displayed path and
     * last save of the current file.
     */
    public class UpdateFooter implements Observer {
        Observable observable;

        UpdateFooter(Observable observable){
            this.observable = observable;
            observable.addObserver(this);
        }

        @Override
        public void update(Observable o, Object arg) {
            if (file.exists()) {
                String text = file.getDateForLastEdited();
                lastSaved.setText("Last saved: " + text);
                String path = file.getPath();
                filePath.setText("Path: " + path);
            } else {
                filePath.setText("");
                lastSaved.setText("Not saved");
            }

        }
    }

}
