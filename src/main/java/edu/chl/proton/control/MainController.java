package edu.chl.proton.control;

import com.jfoenix.controls.JFXTabPane;
import edu.chl.proton.model.DocumentType;
import edu.chl.proton.model.IWorkspace;
import edu.chl.proton.model.WorkspaceFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;

import java.io.IOException;

/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 */
public class MainController {
    private static IWorkspace workspace;
    private FXMLLoader loader;

    @FXML
    private JFXTabPane tabPane;

    public void initialize() throws IOException {
        workspace = (new WorkspaceFactory()).getWorkspace();
        loader = new FXMLLoader(getClass().getResource("/edu/chl/proton/view/markdown-tab.fxml"));
        Tab tab = new Tab("Untitled");
        tab.setContent(loader.load());
        tabPane.getTabs().add(tab);
    }

    public void addNewTab(String name) throws IOException {
        Tab tab = new Tab(name);
        tab.setContent(loader.load());
        tabPane.getTabs().add(tab);
    }

    @FXML
    public void onClickNewButton(ActionEvent event) throws IOException {
        workspace.createDocument(DocumentType.MARKDOWN);
        addNewTab("Untitled.md");
    }

    @FXML
    public void onClickOpenButton(ActionEvent event) throws IOException {
        workspace.openDocument(file);
    }

    @FXML
    public void onClickSaveButton(ActionEvent) throws IOException {
        workspace.saveCurrentDocument();
    }

    @FXML
    public void onClickUndoButton(ActionEvent event) throws IOException {

    }

    @FXML
    public void onClickRedoButton(ActionEvent event) throws IOException {

    }

    @FXML
    public void onClickHeadingButton(ActionEvent event) throws IOException {

    }

    @FXML
    public void onClickBoldButton(ActionEvent event) throws IOException {

    }

    @FXML
    public void onClickItalicButton(ActionEvent event) throws IOException {

    }

    @FXML
    public void onClickQuoteButton(ActionEvent event) throws IOException {

    }

    @FXML
    public void onClickImageButton(ActionEvent event) throws IOException {

    }

    @FXML
    public void onClickCodeButton(ActionEvent event) throws IOException {

    }

    @FXML
    public void onClickOrderedListButton(ActionEvent event) throws IOException {

    }

    @FXML
    public void onClickUnorderedListButton(ActionEvent event) throws IOException {

    }
}
