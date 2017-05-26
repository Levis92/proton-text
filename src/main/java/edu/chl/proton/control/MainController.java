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

import java.io.*;
import java.util.*;

import static java.lang.Boolean.TRUE;


/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 */
public class MainController {
    private static IFileHandler file;
    private static IDocumentHandler document;
    private IStageHandler stage;
    private static SingleSelectionModel<Tab> selectionModel;
    private static Observable observable;
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
        new UpdateFooter(observable);
        filePath.setText("");
        lastSaved.setText("Not saved");
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        treeView.managedProperty().bind(treeView.visibleProperty());
        selectionModel = tabPane.getSelectionModel();
        menuBar.useSystemMenuBarProperty().set(true);
        addNewTab("Untitled.md");
        document.createDocument(DocumentType.MARKDOWN);
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

        if (file != null && file.isFile() && !fileIsOpened()) {
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
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static SingleSelectionModel<Tab> getSelectionModel() {
        return selectionModel;
    }

    static boolean fileIsOpened() {
        return isOpened;
    }

    static void fileHasOpened() {
        isOpened = false;

    }

    private void addNewTab(String name) throws IOException {
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
        openFile(file);
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
            MainController.file.setCurrentDirectory(file);
            File currentDir = new File(MainController.file.getCurrentDirectory().getPath());
            fileTree.populateTree(currentDir, null);
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
        if (file.exists()) {
            String path = "./" + file.getPath().substring(file.getPath().lastIndexOf('/')+1);
            String title = "Set new name";
            TextPrompt prompt = new TextPrompt(stage.getStage(), title, path);
            String newName = checkCorrectFileName(prompt, title).getResult();
            if (newName!=null) {
                file.saveCurrentDocument(newName);
            }
        } else {

        }
    }

    public void onClickSaveAs(ActionEvent actionEvent) throws IOException {
        String path ="./filename.md";
        if (file.exists()){
            path = "./" + file.getPath().substring(file.getPath().lastIndexOf('/'));
        }
        String title = "Save file as";
        TextPrompt prompt = new TextPrompt(stage.getStage(),title,path);
        String newName=checkCorrectFileName(prompt, title).getResult();
        if (newName!=null) {
            file.saveCurrentDocument(newName);
        }

    }

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
    /*
    if(prompt.getResult()==null) {
                TextPrompt prompt1 = new TextPrompt(stage.getStage(),title,"./tryAgain.txt");
                checkCorrectFileName(prompt1, title);
            }
            String ext1 = FilenameUtils.getExtension("/path/to/file/foo.txt"); // returns "txt"
            String ext2 = FilenameUtils.getExtension("bar.exe"); // returns "exe"
     */

    public void onClickCloseApplication(ActionEvent event) {
        String title = "Close application";
        String message = "Are you sure you want to quit Proton Text?";
        PopupWindow popup = new PopupWindow(stage.getStage(),title,message);
        if (popup.resultIsYes()) stage.getStage().close();
    }


    @FXML
    public void onClickCloseCurrentTab(ActionEvent event) {
        document.removeCurrentDocument();
        tabPane.getTabs().removeAll(selectionModel.getSelectedItem());
    }

    @FXML
    public void onClickCloseAllTabs(ActionEvent event) {
        document.removeAllDocuments();
        int count = tabPane.getTabs().size();
        tabPane.getTabs().remove(0, count-1);
    }

    public void onClickAbout(ActionEvent actionEvent) {
        new MessageDialog(stage.getStage(),"About Proton Text","Proton Text is a " +
                "text editor created by students at Chalmers University of Technology. \n" +
                "Course: TDA367");
    }

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
