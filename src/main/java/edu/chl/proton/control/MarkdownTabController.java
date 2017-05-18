package edu.chl.proton.control;

import edu.chl.proton.model.IDocumentHandler;
import edu.chl.proton.model.IFileHandler;
import edu.chl.proton.model.WorkspaceFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.web.HTMLEditor;

import java.io.IOException;


/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 */
public class MarkdownTabController {
    private static IFileHandler file;
    private static IDocumentHandler document;

    @FXML
    HTMLEditor htmlEditor;

    public void initialize() {
        WorkspaceFactory factory = new WorkspaceFactory();
        file = factory.getWorkspace();
        document = factory.getWorkspace();
        hideHTMLEditorToolbars(htmlEditor);
    }

    // Found at http://stackoverflow.com/questions/10075841/how-to-hide-the-controls-of-htmleditor
    public static void hideHTMLEditorToolbars(final HTMLEditor editor)
    {
        editor.setVisible(false);
        Platform.runLater(() -> {
            Node[] nodes = editor.lookupAll(".tool-bar").toArray(new Node[0]);
            for(Node node : nodes)
            {
                node.setVisible(false);
                node.setManaged(false);
            }
            editor.setVisible(true);
        });
    }

    public static void setTextFlow() {
        //List<Text> text = document.getText();
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
            // Position.setX(Position.getX()-2)?

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
        // Go to new line.
        document.insertPart("*** ");
        // Go to new line.
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
        document.insertPart("****");
    }

}
