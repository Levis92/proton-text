package edu.chl.proton.control;

import com.jfoenix.controls.JFXTabPane;
import edu.chl.proton.model.*;
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
    private static IFileHandler file;
    private static IDocumentHandler document;
    private FXMLLoader loader;

    @FXML
    private JFXTabPane tabPane;

    public void initialize() throws IOException {
        WorkspaceFactory factory = new WorkspaceFactory();
        file = factory.getWorkspace();
        document = factory.getWorkspace();
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

    @FXML
    public void onClickLinkButton(ActionEvent event) throws IOException {

    }

    @FXML
    public void onClickHeadingButton(ActionEvent event) throws IOException {

    }

    @FXML
    public void onClickBoldButton(ActionEvent event) throws IOException {
        // Four asterixes and move cursor two steps back. Method in Document that takes in
        // this and updates the aktuella line?
        document.insertPart("****");
        // Position.setX(Position.getX()-2);
    }

    @FXML
    public void onClickItalicButton(ActionEvent event) throws IOException {
        document.insertPart("**");
    }

    @FXML
    public void onClickQuoteButton(ActionEvent event) throws IOException {
        // Go to beginning of line. Set cursor?
        // Position.setX(0);
        document.insertPart("> ");
    }

    @FXML
    public void onClickImageButton(ActionEvent event) throws IOException {

    }

    @FXML
    public void onClickCodeButton(ActionEvent event) throws IOException {

    }

    @FXML
    public void onClickOrderedListButton(ActionEvent event) throws IOException {
        // Go to beginning of line
        document.insertPart("1.   ");//the actual number has no importance.
        // Should it repeat itself?
    }

    @FXML
    public void onClickUnorderedListButton(ActionEvent event) throws IOException {
        // Go to beginning of line
        document.insertPart("*   ");
    }

    @FXML
    public void onClickHorizontalLineButton(ActionEvent event) throws IOException {

    }
}
