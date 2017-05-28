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
