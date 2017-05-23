package edu.chl.proton.control;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import edu.chl.proton.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 */
public class MarkdownTabController {
    private static IFileHandler file;
    private static IDocumentHandler document;
    private IStageHandler stage;

    @FXML
    HTMLEditor htmlEditor;
    @FXML
    WebView webView;

    public void initialize() {
        WorkspaceFactory factory = new WorkspaceFactory();
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
