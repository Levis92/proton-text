package edu.chl.proton.model;

import java.util.List;

/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-02.
 */
public interface IWorkspace {

    Document getCurrentDocument();

    void saveCurrentDocument();

    void setCurrentDirectory(Folder folder);

    Folder getCurrentDirectory();

    void createDocument(DocumentType type);

    void openDocument(String filePath);

    void removeDocument(Document doc);

    void setDirectory(String folderPath);

    String getDirectory();

    void setText(List<String> text);

    List<String> getText();
}
