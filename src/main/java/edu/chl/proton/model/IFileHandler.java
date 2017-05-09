package edu.chl.proton.model;

import java.io.IOException;

/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-02.
 */
public interface IFileHandler {

    void saveCurrentDocument() throws IOException;

    void setCurrentDirectory(Folder folder);

    Folder getCurrentDirectory();

    void setDirectory(String folderPath);

    String getDirectory();


}
