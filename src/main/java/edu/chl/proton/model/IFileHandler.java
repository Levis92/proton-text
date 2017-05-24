package edu.chl.proton.model;

import java.io.*;
import java.io.File;

/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-02.
 */
public interface IFileHandler {

    void saveCurrentDocument() throws IOException;

    void setCurrentDirectory(FileUtility directory) throws  IOException;

    String getCurrentDirectory();

    void setDirectory(String folderPath);

    File getDirectory();

    File getLastEditedFile(String dirPath);


}
