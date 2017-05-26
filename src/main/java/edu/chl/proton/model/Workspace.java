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
    private File currentDirectory;
    private DocumentFactory factory = new DocumentFactory();

    public Workspace() throws IOException {
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
    public int getCurrentDocument() {
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
    public void setCurrentDirectory(File directory) throws  IOException {
        if(!directory.isDirectory()) {
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
        setChanged();
        notifyObservers();
    }

    @Override
    public void removeCurrentDocument() {
        if (tabs.contains(currentDocument)) {
            tabs.remove(currentDocument);
            if (tabs.isEmpty()) currentDocument = null;
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public void removeDocument(int index) {
        if (tabs.contains(tabs.get(index))) {
            tabs.remove(tabs.get(index));
            if (tabs.isEmpty()) currentDocument = null;
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
    public void setDirectory(File folder) {
        if (folder.isDirectory()) {
            currentDirectory = folder;
        }
    }

    @Override
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
    public boolean isSaved() {
        return currentDocument.isSaved();
    }

    @Override
    public boolean exists() {
        return currentDocument.doesExist();
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
