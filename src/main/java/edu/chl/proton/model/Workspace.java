package edu.chl.proton.model;

import edu.chl.proton.Protontext;
import javafx.stage.Stage;
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
    private Folder currentDirectory;
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

    public void setCurrentDirectory(Folder folder) {
        currentDirectory = folder;
    }

    public String getCurrentDirectory() {
        return currentDirectory == null ? "./" : currentDirectory.getPath();
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

    @Override
    public String getDirectory() {
        return null;
    }

    public void setDirectory(Folder folder) {
        currentDirectory = folder;
    }

    public Folder getDirectory(Folder folder) {
        return currentDirectory;
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
