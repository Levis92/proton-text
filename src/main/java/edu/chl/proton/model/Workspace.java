package edu.chl.proton.model;

import edu.chl.proton.Protontext;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 */

public class Workspace extends Observable implements IFileHandler, IDocumentHandler, IStageHandler {
    private List<Document> tabs = new ArrayList<>();
    private Document currentDocument;
    private java.io.File currentDirectory;
    private DocumentFactory factory = new DocumentFactory();

    public Workspace() {
        currentDocument = factory.createDocument(DocumentType.MARKDOWN);
        tabs.add(currentDocument);
        //setCurrentDirectory(new Folder("Root"));
    }


    public void setCurrentDocument(int index) {
        currentDocument = tabs.get(index);
    }

    public int getCurrentDocument() {
        return tabs.indexOf(currentDocument);
    }

    public void saveCurrentDocument() throws IOException {
       currentDocument.save();
    }

    public void setCurrentDirectory(FileUtility directory) throws  IOException {
        if(!directory.isDirectory()) {
            throw new IOException("Trying to set a file as directory");
        }

        currentDirectory = directory;
    }

    public String getCurrentDirectory() {
        return currentDirectory == null ? "./Proton Text Directory" : currentDirectory.getPath();
    }


    public void createDocument(DocumentType type) {
        Document doc = factory.createDocument(type);
        currentDocument = doc;
        tabs.add(doc);
    }

    @Override
    public void openDocument(String filePath) {
        Document doc = factory.getDocument(filePath);
        currentDocument = doc;
        tabs.add(doc);
    }

    @Override
    public void removeCurrentDocument() {

    }

    public void removeDocument(int index) {
        if (tabs.contains(tabs.get(index))) {
            tabs.remove(tabs.get(index));
        }
    }

    @Override
    public void setDirectory(String folderPath) {

    }

    public void setDirectory(FileUtility folder) {
        currentDirectory = folder;
    }

    public File getDirectory() {
        return currentDirectory;
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
    public void setText(List<String> text) {
        currentDocument.setText(text);
        setChanged();
        notifyObservers();

    }

    @Override
    public String getText() {
        return currentDocument.getText();
    }

    @Override
    public void insertPart(String part) {
        currentDocument.insertPart(part);
    }

    @Override
    public String getHTML() {
        return null;
    }

    @Override
    public Stage getStage() {
        return Protontext.getStage();
    }
}
