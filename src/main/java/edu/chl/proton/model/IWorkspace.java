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

    void openDocument(File file);

    void removeDocument(Document doc);

    void setDirectory(Folder folder);

    Folder getDirectory(Folder folder);

    void setText(List<String> text);

    List<String> getText();
}
