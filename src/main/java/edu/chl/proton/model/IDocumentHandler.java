package edu.chl.proton.model;

import java.util.List;

/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-07.
 */
public interface IDocumentHandler {

    int getCurrentDocument();

    void setCurrentDocument(int index);

    void createDocument(DocumentType type);

    void openDocument(String filePath);

    void removeCurrentDocument();

    void removeDocument(int index);

    void setText(List<String> text);

    String getText();

    String getHTML();

}
