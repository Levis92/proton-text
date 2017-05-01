package edu.chl.proton.control;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 */
public class MarkdownTabController {
    @FXML
    private TextFlow textFlow;
    public void initialize() {
        Text text1 = new Text("Example text");
        textFlow.getChildren().add(text1);
    }
}
