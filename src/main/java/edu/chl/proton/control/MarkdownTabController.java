package edu.chl.proton.control;

import edu.chl.proton.model.IWorkspace;
import edu.chl.proton.model.WorkspaceFactory;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.List;


/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 */
public class MarkdownTabController {
    private static IWorkspace workspace;

    @FXML
    private TextFlow textFlow;

    public void initialize() {
        workspace = (new WorkspaceFactory()).getWorkspace();
        Text text1 = new Text("Example text");
        textFlow.getChildren().add(text1);
    }

    public static void setTextFlow() {
        List<String> text = workspace.getText();
    }
}
