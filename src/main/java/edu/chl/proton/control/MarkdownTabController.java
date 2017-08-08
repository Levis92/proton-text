/*
 * Proton Text - A Markdown text editor
 * Copyright (C) 2017  Anton Levholm, Ludvig Ekman, Mickaela Södergren
 * and Stina Werme
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.chl.proton.control;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;
import edu.chl.proton.model.workspace.IDocumentHandler;
import edu.chl.proton.model.workspace.IFileHandler;
import edu.chl.proton.model.workspace.IStageHandler;
import edu.chl.proton.model.workspace.WorkspaceFactory;
import edu.chl.proton.view.TextPrompt;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;


/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 *
 * This class controls all functionality of open tabs (Markdown documents).
 */
public class MarkdownTabController {
    private static IFileHandler file;
    private static IDocumentHandler document;
    private IStageHandler stage;

    @FXML
    private HTMLEditor htmlEditor;
    @FXML
    private WebView webView;


    public void initialize() throws IOException {
        WorkspaceFactory factory = new WorkspaceFactory();
        Observable observable = factory.getWorkspace().getCurrentDocument();
        new UpdateView(observable);
        file = factory.getWorkspace();
        document = factory.getWorkspace();
        stage = factory.getWorkspace();
        hideHTMLEditorToolbars(htmlEditor);

        htmlEditor.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (isValidEvent(event)) {
                    document.setText(htmlEditor.getHtmlText());
                }
            }

            private boolean isValidEvent(KeyEvent event) {
                return !isSelectAllEvent(event)
                        && ((isPasteEvent(event)) || isCharacterKeyReleased(event));
            }

            private boolean isSelectAllEvent(KeyEvent event) {
                return event.isShortcutDown() && event.getCode() == KeyCode.A;
            }

            private boolean isPasteEvent(KeyEvent event) {
                return event.isShortcutDown() && event.getCode() == KeyCode.V;
            }

            private boolean isCharacterKeyReleased(KeyEvent event) {
                // Make custom changes here..
                switch (event.getCode()) {
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

    /**
     * Hides the default toolbar in HTMLEditor.
     * @param editor
     */

    // Found at http://stackoverflow.com/questions/10075841/how-to-hide-the-controls-of-htmleditor
    private static void hideHTMLEditorToolbars(final HTMLEditor editor) {
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

    /**
     * Generates a PDF from the HTML String that getHTML() in IDocumentHandler returns.
     * @param event
     * @throws IOException
     * @throws DocumentException
     */

    @FXML
    public void onClickGeneratePDF(ActionEvent event) throws IOException, DocumentException {
        String path = file.getCurrentDirectory().getPath() + "/untitled.pdf";
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

    /**
     * Creates text template for inserting of links
     * @param event
     * @throws IOException
     */
    @FXML
    public void onClickLinkButton(ActionEvent event) throws IOException {
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "[text about link](http://url here)");
    }

    /**
     * Creates text template for inserting of headers
     * @param event
     * @throws IOException
     */
    @FXML
    public void onClickHeadingButton(ActionEvent event) throws IOException {
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "#");
    }

    /**
     * Creates text template for inserting of bolding code
     * @param event
     * @throws IOException
     */
    @FXML
    public void onClickBoldButton(ActionEvent event) throws IOException {
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "****");
    }

    /**
     * Creates text template for inserting of italic text
     * @param event
     * @throws IOException
     */
    @FXML
    public void onClickItalicButton(ActionEvent event) throws IOException {
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "**");
    }


    @FXML
    public void onClickQuoteButton(ActionEvent event) throws IOException {
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "\n> ");
    }

    /**
     * Inserts image from file directory with help from user through file choosing,
     * or if image from web: creates text template.
     * @param event
     * @throws IOException
     */
    @FXML
    public void onClickImageButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Is the image in your file system?",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.NO) {
            WebView webView = (WebView) htmlEditor.lookup("WebView");
            WebPage webPage = Accessor.getPageFor(webView.getEngine());
            webPage.executeCommand("insertText", "![text about image](Image URL)");
        } else {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose image");
            File file = fileChooser.showOpenDialog(stage.getStage());
            if (file != null && file.isFile()) {
                if (isImage(file.getPath())) {
                    WebView webView = (WebView) htmlEditor.lookup("WebView");
                    WebPage webPage = Accessor.getPageFor(webView.getEngine());
                    webPage.executeCommand("insertText", "![text about image](" + file.toURI() + ")");
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR, "File choosen is not an image.");
                    alert2.showAndWait();
                }
            }
        }
    }

    /**
     * Creates text template for inserting of code text
     * @param event
     * @throws IOException
     */
    @FXML
    public void onClickCodeButton(ActionEvent event) throws IOException {
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "´´´´´´");
    }

    /**
     * Creates text template for inserting of ordered listing
     * @param event
     * @throws IOException
     */
    @FXML
    public void onClickOrderedListButton(ActionEvent event) throws IOException {
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "\n1.   ");
    }

    /**
     * Creates text template for inserting of unordered listing
     * @param event
     * @throws IOException
     */
    @FXML
    public void onClickUnorderedListButton(ActionEvent event) throws IOException {
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "\n*     ");
    }

    /**
     * Creates markdown text for horizontal line
     * @param event
     * @throws IOException
     */
    @FXML
    public void onClickHorizontalLineButton(ActionEvent event) throws IOException {
        WebView webView = (WebView) htmlEditor.lookup("WebView");
        WebPage webPage = Accessor.getPageFor(webView.getEngine());
        webPage.executeCommand("insertText", "\n*****\n");
    }

    public class UpdateView implements Observer {
        Observable observable;

        UpdateView(Observable observable){
            this.observable = observable;
            observable.addObserver(this);
        }


        @Override
        public void update(Observable o, Object arg) {
                if (MainController.fileIsOpened()) {
                    String text = document.getText();
                    htmlEditor.setHtmlText(text);
                    MainController.fileHasOpened();
                }
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
