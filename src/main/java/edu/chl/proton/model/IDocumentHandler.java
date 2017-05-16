package edu.chl.proton.model;

import java.util.List;

/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-07.
 */
public interface IDocumentHandler {

    Document getCurrentDocument();

    void createDocument(DocumentType type);

    void openDocument(String filePath);

    void removeCurrentDocument();

    void removeDocument(Document doc);

    void setText(List<String> text);

    List<String> getText();

    void insertPart(String part);

}
