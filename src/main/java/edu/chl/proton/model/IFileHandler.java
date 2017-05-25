package edu.chl.proton.model;

import java.io.*;
import java.io.File;

/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-02.
 */
public interface IFileHandler {

    boolean saveCurrentDocument() throws IOException;

    void saveCurrentDocument(String filepath) throws IOException;

    void setCurrentDirectory(File directory) throws  IOException;

    File getCurrentDirectory();

    void setDirectory(File file);

    File getDirectory();

    File getLastEditedFile(String dirPath);

    String getDateForLastEdited();

    String getPath();

    boolean isSaved();

    boolean exists();
}
