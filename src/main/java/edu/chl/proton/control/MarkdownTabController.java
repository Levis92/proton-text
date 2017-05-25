package edu.chl.proton.control;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;
import edu.chl.proton.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 */
public class MarkdownTabController {
    private static IFileHandler file;
    private static IDocumentHandler document;
    private IStageHandler stage;
    private Observable observable;

    @FXML
    HTMLEditor htmlEditor;
    @FXML
    WebView webView;



    public void initialize() throws IOException {
        WorkspaceFactory factory = new WorkspaceFactory();
        observable = factory.getWorkspace();
        UpdateView view = new UpdateView(observable);
        file = factory.getWorkspace();
        document = factory.getWorkspace();
        stage = factory.getWorkspace();
        hideHTMLEditorToolbars(htmlEditor);

        htmlEditor.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                if (isValidEvent(event))
                {
                    String text = htmlEditor.getHtmlText();
                    List<String> doc;
                    doc = html2text(text);
                    document.setText(doc);
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
    public void onClickGeneratePDF(ActionEvent event) throws IOException, DocumentException {
        String path = "./test.pdf";
        String title = "Output filepath";
        TextPrompt prompt = new TextPrompt(stage.getStage(),title,path);
        if ((path = prompt.getResult()) != null) {
            com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(path));
            writer.setInitialLeading(12);
            doc.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, doc,
                    new ByteArrayInputStream(document.getHTML().getBytes()));
            doc.close();
        }
    }

    @FXML
    public void onClickLinkButton(ActionEvent event) throws IOException {
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "[text about link](http://url here)");
    }

    @FXML
    public void onClickHeadingButton(ActionEvent event) throws IOException {
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "#");
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
        webPage.executeCommand("insertText", "\n> ");
    }

    @FXML
    public void onClickImageButton(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose image");
        File file = fileChooser.showOpenDialog(stage.getStage());
        if (file != null && file.isFile()) {
            if(isImage(file.getPath())) {
                WebView webView = (WebView) htmlEditor.lookup("WebView");
                WebPage webPage = Accessor.getPageFor(webView.getEngine());
                webPage.executeCommand("insertText", "![text about image](" + file.getPath() + ")");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "File choosen is not an image.");
                alert.showAndWait();
            }

        }

    }
    /*I onClickImageButton() så skulle du kunna implementera med FileChooser
    (som när man öppnar en fil) så att man väljer en bild och så plockar man path:en till
    filen och stoppar in den istället för 'url' när man insert:ar syntaxen 🙂*/

    @FXML
    public void onClickCodeButton(ActionEvent event) throws IOException {
        // Go to new line.
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "\n     ");
        // Go to new line.
    }

    @FXML
    public void onClickOrderedListButton(ActionEvent event) throws IOException {
        // Go to beginning of line
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "\n1.   ");

        // Should it repeat itself?
    }

    @FXML
    public void onClickUnorderedListButton(ActionEvent event) throws IOException {
        // Go to beginning of line
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "\n*     ");

    }

    @FXML
    public void onClickHorizontalLineButton(ActionEvent event) throws IOException {
        //newline
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "\n*****\n");
        //newline
    }

    public class UpdateView implements Observer {
        Observable observable;
        public UpdateView(Observable observable){
            this.observable = observable;
            observable.addObserver(this);
        }


        @Override
        public void update(Observable o, Object arg) {
            String text = document.getText();
            //htmlEditor.setHtmlText(text);
            String html = document.getHTML();
            webView.getEngine().loadContent(html);
        }
    }
    public boolean isImage(String string){
        if(string.substring(string.length()-4).equals(".pdf") ||
                string.substring(string.length()-4).equals(".gif") ||
                string.substring(string.length()-4).equals(".png") ||
                string.substring(string.length()-4).equals(".jpg") ||
                string.substring(string.length()-5).equals(".jpeg") ||
                string.substring(string.length()-4).equals(".tif") ||
                string.substring(string.length()-4).equals(".bmp")){
            return true;
        }
        return false;

    }

}
