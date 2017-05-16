package edu.chl.proton.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 */
public class Workspace implements IFileHandler, IDocumentHandler {
    private List<Document> tabs = new ArrayList<>();
    private Document currentDocument;
    private Folder currentDirectory;
    private DocumentFactory factory = new DocumentFactory();

    public Workspace() {}


    public void setCurrentDocument(Document doc) {
        currentDocument = doc;
    }

    public Document getCurrentDocument() {
        return currentDocument;
    }

    public void saveCurrentDocument() throws IOException {
       currentDocument.save();
    }

    public void setCurrentDirectory(Folder folder) {
        currentDirectory = folder;
    }

    public Folder getCurrentDirectory() {
        return currentDirectory;
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

    }

    @Override
    public List<String> getText() {
        return new ArrayList<>();
    }

    @Override
    public void insertPart(String part) {
        currentDocument.insertPart(part);
    }
}
