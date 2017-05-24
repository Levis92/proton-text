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
    private FileUtility currentDirectory;
    private DocumentFactory factory = new DocumentFactory();

    public Workspace() throws IOException {
        currentDocument = factory.createDocument(DocumentType.MARKDOWN);
        tabs.add(currentDocument);
        setCurrentDirectory(new FileUtility("./Proton Text Directory"));
    }

    @Override
    public void setCurrentDocument(int index) {
        currentDocument = tabs.get(index);
    }

    @Override
    public int getCurrentDocument() {
        return tabs.indexOf(currentDocument);
    }

    @Override
    public boolean saveCurrentDocument() throws IOException {
       return currentDocument.save();
    }

    @Override
    public void saveCurrentDocument(String filepath) throws IOException {
        currentDocument.save(filepath);
    }

    @Override
    public void setCurrentDirectory(File directory) throws  IOException {
        if(!directory.isDirectory()) {
            throw new IOException("Trying to set a file as directory");
        }
        currentDirectory = (FileUtility) directory;
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
    }

    @Override
    public void openDocument(String filePath) {
        Document doc = factory.getDocument(filePath);
        currentDocument = doc;
        tabs.add(doc);
    }

    @Override
    public void removeCurrentDocument() {
        if (tabs.contains(currentDocument)) {
            tabs.remove(currentDocument);
        }
    }

    @Override
    public void removeDocument(int index) {
        if (tabs.contains(tabs.get(index))) {
            tabs.remove(tabs.get(index));
        }
    }

    @Override
    public void setDirectory(File folder) {
        if (folder.isDirectory()) {
            currentDirectory = (FileUtility) folder;
        }
    }

    @Override
    public File getDirectory() {
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
    public String getHTML() {
        return currentDocument.getHTML();
    }

    @Override
    public Stage getStage() {
        return Protontext.getStage();
    }
}
