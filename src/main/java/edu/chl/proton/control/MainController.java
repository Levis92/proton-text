package edu.chl.proton.control;

import edu.chl.proton.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 */
public class MainController {
    private static IFileHandler file;
    private static IDocumentHandler document;
    private IStageHandler stage;
    SingleSelectionModel<Tab> selectionModel;
    private Observable observable;

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
        new UpdateFooter(observable);
        filePath.setText("");
        lastSaved.setText("Not saved");
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        treeView.managedProperty().bind(treeView.visibleProperty());
        selectionModel = tabPane.getSelectionModel();
        menuBar.useSystemMenuBarProperty().set(true);
        addNewTab("Untitled.md");
        document.createDocument(DocumentType.MARKDOWN);

        File currentDir = new File(file.getCurrentDirectory().getPath()); // current directory
        findFiles(currentDir, null);

        treeView.setEditable(true);
        //treeView.setShowRoot(false);
        treeView.setCellFactory(p -> new EditableTreeCell());
    }


    private void findFiles(File dir, TreeItem<File> parent) {
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
                findFiles(file, root);
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


    public void addNewTab(String name) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/chl/proton/view/markdown-tab.fxml"));
        Tab tab = new Tab(name);
        tab.getStyleClass().add("tab");
        tab.setContent(loader.load());
        selectionModel.select(tab);
        tabPane.getTabs().add(tab);
        tab.setOnSelectionChanged(e -> document.setCurrentDocument(selectionModel.getSelectedIndex()));
        tab.setOnCloseRequest(e -> document.removeDocument(tabPane.getTabs().indexOf(e.getTarget())));
    }

    @FXML
    public void onClickNextTab(ActionEvent event) throws IOException {
        selectionModel.selectNext();
        document.setCurrentDocument(selectionModel.getSelectedIndex());
    }

    @FXML
    public void onClickPreviousTab(ActionEvent event) throws IOException {
        selectionModel.selectPrevious();
        document.setCurrentDocument(selectionModel.getSelectedIndex());
    }

    @FXML
    public void onClickNewButton(ActionEvent event) throws IOException {
        document.createDocument(DocumentType.MARKDOWN);
        addNewTab("Untitled.md");
    }

    @FXML
    public void onClickOpenButton(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file");
        File file = fileChooser.showOpenDialog(stage.getStage());
        if (file != null && file.isFile()) {
            document.openDocument(file.getPath());
            addNewTab(file.getName());
            try(BufferedReader br = new BufferedReader(new FileReader(file))) {
                List<String> lines = new ArrayList<>();
                String line;

                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
                document.setText(lines);
            }
        }
    }

    @FXML
    public void onClickSaveButton(ActionEvent event) throws IOException {
        if (!file.saveCurrentDocument()) {
            String title = "Filepath";
            String input = file.getCurrentDirectory().getPath() + "/filename.md";
            TextPrompt prompt = new TextPrompt(stage.getStage(),title,input);
            if ((input = prompt.getResult()) != null) {
                file.saveCurrentDocument(input);
            }
        }
    }

    @FXML
    public void onClickUndoButton(ActionEvent event) throws IOException {
        // Can be really hard to implement.
    }

    @FXML
    public void onClickRedoButton(ActionEvent event) throws IOException {

    }

    @FXML
    public void onClickChangeDirectory(ActionEvent event) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Change directory");
        File file = directoryChooser.showDialog(stage.getStage());
        if (file != null && file.isDirectory()) {
            this.file.setCurrentDirectory(file);
            File currentDir = new File(this.file.getCurrentDirectory().getPath());
            findFiles(currentDir, null);
        }
    }

    @FXML
    public void onClickToggleTreeViewVisibility(ActionEvent event) throws IOException {
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

    public void onClickCloseApplication(ActionEvent event) {
        String title = "Close application";
        String message = "Are you sure you want to quit Proton Text?";
        PopupWindow popup = new PopupWindow(stage.getStage(),title,message);
        if (popup.resultIsYes()) stage.getStage().close();
    }

    public class UpdateFooter implements Observer {
        Observable observable;
        public UpdateFooter(Observable observable){
            this.observable = observable;
            observable.addObserver(this);
        }


        @Override
        public void update(Observable o, Object arg) {
            if (file.exists()) {
                String text = file.getDateForLastEdited();
                lastSaved.setText("Lased saved: " + text);
                String path = file.getPath();
                filePath.setText("Path: " + path);
            } else {
                filePath.setText("");
                lastSaved.setText("Not saved");
            }

        }
    }
}
