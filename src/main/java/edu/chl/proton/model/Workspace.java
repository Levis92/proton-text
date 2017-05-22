package edu.chl.proton.model;

import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 */
public class Workspace implements IFileHandler, IDocumentHandler {
    private List<Document> tabs = new ArrayList<>();
    private Document currentDocument;
    private java.io.File currentDirectory;
    private DocumentFactory factory = new DocumentFactory();

    public Workspace() {
        currentDocument = factory.createDocument(DocumentType.MARKDOWN);
        tabs.add(currentDocument);
        //setCurrentDirectory(new Folder("Root"));
    }


    public void setCurrentDocument(Document doc) {
        currentDocument = doc;
    }

    public Document getCurrentDocument() {
        return currentDocument;
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
        factory.createDocument(type);
    }

    @Override
    public void openDocument(String filePath) {
        factory.getDocument(filePath);

    }

    @Override
    public void removeCurrentDocument() {

    }

    public void removeDocument(Document doc) {
        if (tabs.contains(doc)) {
            tabs.remove(doc);
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
    public void setText(List<String> text) {
        currentDocument.setText(text);
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
}
