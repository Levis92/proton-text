package edu.chl.proton.model;

import javafx.scene.text.Text;

import java.util.List;

/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-07.
 */
public interface IDocumentHandler {

    Document getCurrentDocument();

    void createDocument(DocumentType type);

    void openDocument(String filePath);

    void removeDocument(Document doc);

    void setText(List<Text> text);

    List<Text> getText();

}
