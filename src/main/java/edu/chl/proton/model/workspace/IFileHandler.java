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

package edu.chl.proton.model.workspace;

import java.io.File;
import java.io.IOException;

/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-05-02.
 *
 * An interface for all methods that are related to file management.
 */
public interface IFileHandler {

    /**
     * Tries to save the current document and returns a boolean to notify if it succeeded.
     * @return the result of trying to save a file.
     * @throws IOException if there is no file to save.
     */
    boolean saveCurrentDocument() throws IOException;

    /**
     * Saves the current document to selected file path.
     * @param filepath to where to save the file.
     * @throws IOException if the method fails to create the file.
     */
    void saveCurrentDocument(String filepath) throws IOException;

    /**
     * Sets the current directory to selected directory.
     * @param directory the directory to set as current.
     * @throws IOException if the selected file is not a directory.
     */
    void setCurrentDirectory(File directory) throws  IOException;

    /**
     * Returns the current directory.
     * @return the current directory.
     */
    File getCurrentDirectory();

    /**
     * Returns the path to the last edited file.
     * @param dirPath to file.
     * @return file object of the path.
     */
    File getLastEditedFile(String dirPath);

    /**
     * Returns a String of the date and time for when the file was last edited.
     * @return the date when the file was last edited.
     */
    String getDateForLastEdited();

    /**
     * Returns the path to the file of the current document.
     * @return the path to the file.
     */
    String getPath();

    /**
     * Returns a boolean which says if the document is saved since last edited.
     * @return if the file has been saved since last edited.
     */
    boolean isSaved();

    /**
     * Checks if there exists a file for the document.
     * @return if there exist a file.
     */
    boolean exists();

    /**
     * Checks if the file is already opened.
     * @param file to check.
     * @return index of file if it is opened, else returns -1.
     */
    int isAlreadyOpen(File file);

    /**
     * Removes the file object from document.
     * @param index of document.
     */
    void removeFile(int index);

}