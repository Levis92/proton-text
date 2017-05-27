package edu.chl.proton.model;

import java.io.*;
import java.io.File;

/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-02.
 */
public interface IFileHandler {

    /**
     * Tries to save the current document and returns a boolean to notify if it succeeded.
     * @return
     * @throws IOException
     */

    boolean saveCurrentDocument() throws IOException;

    /**
     * Saves the current document to selected file path.
     * @param filepath
     * @throws IOException
     */

    void saveCurrentDocument(String filepath) throws IOException;

    /**
     * Sets the current directory to selected directory.
     * @param directory
     * @throws IOException
     */

    void setCurrentDirectory(File directory) throws  IOException;

    /**
     * Returns the current directory.
     * @return
     */

    File getCurrentDirectory();

    /**
     * Returns the path to the last edited file.
     * @param dirPath
     * @return
     */

    File getLastEditedFile(String dirPath);

    /**
     * Returns a String of the date and time for when the file was last edited.
     * @return
     */

    String getDateForLastEdited();

    /**
     * Returns the path to the file of the current document.
     * @return
     */

    String getPath();

    /**
     * Returns a boolean which says if the document is saved since last edited.
     * @return
     */

    boolean isSaved();

    /**
     * Checks if there exists a file for the document.
     * @return
     */

    boolean exists();
}
