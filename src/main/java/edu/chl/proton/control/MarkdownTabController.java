package edu.chl.proton.control;

import edu.chl.proton.model.IDocumentHandler;
import edu.chl.proton.model.IFileHandler;
import edu.chl.proton.model.WorkspaceFactory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;

import java.util.List;


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
        List<Text> text = document.getText();
    }

}
