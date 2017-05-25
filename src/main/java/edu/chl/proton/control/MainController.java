package edu.chl.proton.control;

import edu.chl.proton.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.TRUE;


/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 */
public class MainController {
    private static IFileHandler file;
    private static IDocumentHandler document;
    private IStageHandler stage;
    SingleSelectionModel<Tab> selectionModel;

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


    public void initialize() throws IOException {
        WorkspaceFactory factory = new WorkspaceFactory();
        file = factory.getWorkspace();
        document = factory.getWorkspace();
        stage = factory.getWorkspace();
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Change directory");
        File file = fileChooser.showOpenDialog(stage.getStage());
        if (file != null && file.isDirectory()) {
            //this.file.setCurrentDirectory(file);
            //File currentDir = new File(this.file.getCurrentDirectory());
            //findFiles(currentDir, null);
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


    public void onClickRenameFile(ActionEvent actionEvent) throws IOException {
        String path = "./Rename.txt";
        String title = "Set new name";
        TextPrompt prompt = new TextPrompt(stage.getStage(),title,path);
        String newName=checkCorrectFileName(prompt, title).getResult();
        file.saveCurrentDocument(newName);

    }

    public void onClickSaveAs(ActionEvent actionEvent) throws IOException {
        String path = "./oldName.txt";
        String title = "Save file as";
        TextPrompt prompt = new TextPrompt(stage.getStage(),title,path);
        String newName=checkCorrectFileName(prompt, title).getResult();
        file.saveCurrentDocument(newName);

    }

    private TextPrompt checkCorrectFileName(TextPrompt prompt, String title) {
        int pLength = prompt.getResult().length();
        while ( (pLength <7)==TRUE  ||
                !((prompt.getResult()).substring(pLength-4).equals(".pdf") ||
                        (prompt.getResult()).substring(pLength-4).equals(".txt"))
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
    /*
    if(prompt.getResult()==null) {
                TextPrompt prompt1 = new TextPrompt(stage.getStage(),title,"./tryAgain.txt");
                checkCorrectFileName(prompt1, title);
            }
            String ext1 = FilenameUtils.getExtension("/path/to/file/foo.txt"); // returns "txt"
            String ext2 = FilenameUtils.getExtension("bar.exe"); // returns "exe"
     */

    public void onClickCloseApplication(ActionEvent event) {
    }

}
