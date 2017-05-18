package edu.chl.proton.control;

import edu.chl.proton.model.IDocumentHandler;
import edu.chl.proton.model.IFileHandler;
import edu.chl.proton.model.WorkspaceFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

        htmlEditor.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                if (isValidEvent(event))
                {
                    String text = htmlEditor.getHtmlText();
                    System.out.println(text);
                    text = stripHTMLTags(text);
                    int index = text.length()-1;
                    System.out.println("Index: " + index);
                    System.out.println(text.charAt(index));
                    //document.insertPart(htmlEditor.getHtmlText());
                }
            }

            private boolean isValidEvent(KeyEvent event)
            {
                return !isSelectAllEvent(event)
                        && ((isPasteEvent(event)) || isCharacterKeyReleased(event));
            }

            private boolean isSelectAllEvent(KeyEvent event)
            {
                return event.isShortcutDown() && event.getCode() == KeyCode.A;
            }

            private boolean isPasteEvent(KeyEvent event)
            {
                return event.isShortcutDown() && event.getCode() == KeyCode.V;
            }

            private boolean isCharacterKeyReleased(KeyEvent event)
            {
                // Make custom changes here..
                switch (event.getCode())
                {
                    case ALT:
                    case COMMAND:
                    case CONTROL:
                    case SHIFT:
                        return false;
                    default:
                        return true;
                }
            }
        });
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

    private String stripHTMLTags(String htmlText) {
        Pattern pattern = Pattern.compile("<[^>]*>");
        Matcher matcher = pattern.matcher(htmlText);
        final StringBuffer sb = new StringBuffer(htmlText.length());
        while(matcher.find()) {
            matcher.appendReplacement(sb, " ");
        }
        matcher.appendTail(sb);
        return sb.toString().trim();
    }


    @FXML
    public void onClickLinkButton(ActionEvent event) throws IOException {

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

    @FXML
    public void onClickHorizontalLineButton(ActionEvent event) throws IOException {

    }

}
