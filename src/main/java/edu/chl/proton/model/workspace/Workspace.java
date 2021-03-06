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

import edu.chl.proton.Protontext;
import edu.chl.proton.model.documents.*;
import edu.chl.proton.model.util.FileUtility;
import edu.chl.proton.model.util.TextFormat;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 *
 * This class is responsible for keeping track of all currently opened documents and the current directory.
 */

public class Workspace extends Observable implements IFileHandler, IDocumentHandler, IStageHandler {
    private List<IDocument> tabs = new ArrayList<>();
    private IDocument currentDocument;
    private File currentDirectory;
    private DocumentFactory factory = new DocumentFactory();

    Workspace() throws IOException {
        currentDocument = factory.createDocument(DocumentType.MARKDOWN);
        tabs.add(currentDocument);
        setCurrentDirectory(new FileUtility("./Proton Text Directory"));
    }

    @Override
    public void setCurrentDocument(int index) {
        if (tabs.size() > index && tabs.contains(tabs.get(index))) {
            currentDocument = tabs.get(index);
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public Observable getCurrentDocument() {
        return (Observable) currentDocument;
    }

    @Override
    public int getCurrentDocumentIndex() {
        return tabs.indexOf(currentDocument);
    }

    @Override
    public boolean saveCurrentDocument() throws IOException {
        boolean isSaved = currentDocument.save();
        if (isSaved) {
            setChanged();
            notifyObservers();
        }
        return isSaved;

    }

    @Override
    public void saveCurrentDocument(String filepath) throws IOException {
        currentDocument.save(filepath);
        setChanged();
        notifyObservers();
    }

    @Override
    public void setCurrentDirectory(File directory) throws IOException {
        if (!directory.isDirectory()) {
            throw new IOException("Trying to set a file as directory");
        }
        currentDirectory = directory;
    }

    @Override
    public File getCurrentDirectory() {
        return currentDirectory;
    }

    @Override
    public void createDocument(DocumentType type) {
        Document doc = factory.createDocument(type);
        currentDocument = doc;
        tabs.add(doc);
        setChanged();
        notifyObservers();
    }

    @Override
    public void openDocument(String filePath) {
        Document doc = factory.getDocument(filePath);
        currentDocument = doc;
        tabs.add(doc);
        doc.notifyObservers();
        setChanged();
        notifyObservers();
    }

    @Override
    public void removeCurrentDocument() {
        if (tabs.contains(currentDocument)) {
            tabs.remove(currentDocument);
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public void removeDocument(int index) {
        if (tabs.contains(tabs.get(index))) {
            tabs.remove(tabs.get(index));
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public void removeAllDocuments() {
        tabs.removeAll(tabs);
        setChanged();
        notifyObservers();
    }

    @Override
    public File getLastEditedFile(String dirPath) {
        return null;
    }

    @Override
    public String getDateForLastEdited() {
        return currentDocument.getDateForLastEdited();
    }

    @Override
    public String getPath() {
        return currentDocument.getPath();
    }

    @Override
    public boolean isSaved() {
        return currentDocument.isSaved();
    }

    @Override
    public boolean exists() {
        return currentDocument.doesExist();
    }

    @Override
    public void setText(String text) {
        if (currentDocument != null) {
            List<String> doc;
            doc = TextFormat.htmlToText(text);
            currentDocument.setText(doc);
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public void setText(List<String> text) {
        if (currentDocument != null) {
            currentDocument.setText(text);
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public int isAlreadyOpen(File file) {
        for(IDocument doc : tabs){
            if (doc.getFile() != null) {
                if (file.getPath().equals(doc.getPath())) {
                    return tabs.indexOf(doc);
                }
            }
        }
        return -1;
    }

    @Override
    public void removeFile(int index) {
        tabs.get(index).removeFile();
    }

    @Override
    public String getText() {
        return currentDocument.getText();
    }

    @Override
    public String getHTML() {
        return currentDocument.getHTML();
    }

    @Override
    public Stage getStage() {
        return Protontext.getStage();
    }

}