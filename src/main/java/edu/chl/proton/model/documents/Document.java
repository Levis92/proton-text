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

package edu.chl.proton.model.documents;

import edu.chl.proton.model.util.FileUtility;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-01.
 *
 * Keeps track of every tab's properties and handles the text. Provides
 * implementation of those methods that are the same no matter which
 * document type. Uses strategy patternt o delegate the methods that
 * differ in implementation to the correct document type.
 */
public class Document extends Observable implements IDocument {

    private FileUtility file;
    private IDoc docType;

    Document(IDoc type){
        this.docType = type;
        setChanged();
    }

    Document(IDoc type, File file){
        this.docType = type;
        this.file = (FileUtility) file;
        setChanged();
    }

    /**
     * Check to see if there is an existing file yet.
     * @return true if the does exist, otherwise false
     */
    public boolean doesExist(){
        if(file != null){
            return true;
        }
        return false;
    }

    public File getFile(){
        return this.file;
    }

    public String getPath(){
        return file.getPath();
    }

    protected void setFile(File file){
        this.file = (FileUtility) file;
    }

    private List<String> getLines(){
        return docType.getLines();
    }

    public String getText(){
        return docType.getText();
    }

    public void setText(List<String> text){
        docType.setText(text);
        setChanged();
        notifyObservers();
    }

    public String getHTML(){
        return docType.getHTML();
    }

    /**
     * Saves the file.
     * @throws IOException if operation fails.
     */
    public void save(String path) throws IOException{
        file = new FileUtility(path);
        file.save(getLines());
    }

    /**
     * Saves the file.
     * @return true if file was successfully saved
     * @throws IOException if operation fails.
     */
    public boolean save() throws  IOException {
        try{
            file.save(getLines());
            return true;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    /**
     * Removes the file.
     */
    protected void remove(){
        file.remove();
    }

    /**
     * Removes the file.
     */
    public void removeFile() {
        file = null;
    }

    /**
     * Returns the date of the last time the file was edited.
     * @return string with the date of last edit
     */
    public String getDateForLastEdited(){
        return file.getDateForLastEdited();
    }

    /**
     * Checks if the file is saved
     * @return true or false
     */
    public boolean isSaved(){
        return file.isSaved();
    }

    /**
     * Aqcuires the text from the opened file.
     */
    public void aqcuireText() {
        file.aqcuireText();
    }

}