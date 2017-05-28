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

import java.util.List;

/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-07.
 */
public interface IDocumentHandler {

    /**
     * Returns the index of the current document.
     * @return
     */

    int getCurrentDocument();

    /**
     * Sets the current document based on an index.
     * @param index
     */

    void setCurrentDocument(int index);

    /**
     * Creates a document based on DocumentType.
     * @param type
     */

    void createDocument(DocumentType type);

    /**
     * Creates a document based on opened file.
     * @param filePath
     */

    void openDocument(String filePath);

    /**
     * Removes the current document.
     */

    void removeCurrentDocument();

    /**
     * Removes document based on an index.
     * @param index
     */

    void removeDocument(int index);

    /**
     * Removes all documents.
     */

    void removeAllDocuments();

    /**
     * Takes in a List of Strings and adds them to the document.
     * @param text
     */

    void setText(List<String> text);

    /**
     * Returns the text from the document in a format suitable for HTMLEditor.
     * @return
     */

    String getText();

    /**
     * Returns HTML in a format adapted for having a Preview function in a WebbView.
     * @return
     */

    String getHTML();

}
