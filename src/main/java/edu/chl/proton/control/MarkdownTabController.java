package edu.chl.proton.control;

import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;
import edu.chl.proton.event.TextUpdateEvent;
import edu.chl.proton.model.IDocumentHandler;
import edu.chl.proton.model.IFileHandler;
import edu.chl.proton.model.Workspace;
import edu.chl.proton.model.WorkspaceFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;

import javafx.scene.web.WebView;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 */
public class MarkdownTabController {
    private static IFileHandler file;
    private static IDocumentHandler document;
    public EventBus eventBus = new EventBus();

    @FXML
    HTMLEditor htmlEditor;
    @FXML
    WebView webView;

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
                    webView.getEngine().loadContent(text);
                    //System.out.println(text);
                    List<String> doc;
                    doc = html2text(text);
                    /*for (String row : doc) {
                        System.out.println(row);
                    }*/
                    document.setText(doc);
                    System.out.println(document.getText());
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


    // This method will be called when a updateText is posted
    @Subscribe public void updateText(TextUpdateEvent event) {
        //doSomethingWith(event);
        //Uppdatera båda osv.
        //document.getText();
        //document.getHTML();
        System.out.println("JAG ANVÄNDS!!!!");
        /*WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "[link](url)");*/

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

    public static List<String> html2text(String html) {
        ArrayList<String> rowList = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Element table = doc.select("body").get(0);
        Elements rows = table.select("p");

        for (int i = 0; i < rows.size(); i++) {
            Element row = rows.get(i);
            rowList.add(row.text());
        }
        return rowList;
    }


    @FXML
    public void onClickLinkButton(ActionEvent event) throws IOException {
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "[link](git )");
    }

    @FXML
    public void onClickHeadingButton(ActionEvent event) throws IOException {

    }

    @FXML
    public void onClickBoldButton(ActionEvent event) throws IOException {
            // Four asterixes and move cursor two steps back. Method in Document that takes in
            // this and updates the aktuella line?
            // Position.setX(Position.getX()-2)?
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "****");


    }

    @FXML
    public void onClickItalicButton(ActionEvent event) throws IOException {
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "**");
    }
        // 1. Cursors plats 2. setText, med htmltext
    @FXML
    public void onClickQuoteButton(ActionEvent event) throws IOException {
        // Go to beginning of line. Set cursor?
        // Position.setX(0);
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "> ");
    }

    @FXML
    public void onClickImageButton(ActionEvent event) throws IOException {
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "![alt text]([url] \"Title\")");
    }

    @FXML
    public void onClickCodeButton(ActionEvent event) throws IOException {
        // Go to new line.
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "***");
        // Go to new line.
    }

    @FXML
    public void onClickOrderedListButton(ActionEvent event) throws IOException {
        // Go to beginning of line
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "1.   ");

        // Should it repeat itself?
    }

    @FXML
    public void onClickUnorderedListButton(ActionEvent event) throws IOException {
        // Go to beginning of line
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "*     ");

    }

    @FXML
    public void onClickHorizontalLineButton(ActionEvent event) throws IOException {
        //newline
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "*****");
        //newline
    }

}
