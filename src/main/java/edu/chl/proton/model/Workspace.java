package edu.chl.proton.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 */
public class Workspace implements IWorkspace {
    private static Workspace uniqueInstance = new Workspace();
    private List<Document> tabs = new ArrayList<Document>();
    private Document currentDocument;
    private Folder currentDirectory;
    private DocumentFactory factory = new DocumentFactory();
    public enum docType {MARKDOWN, PLAIN};

    private Workspace() {}

    public static Workspace getInstance() {
        return uniqueInstance;
    }

    public void setCurrentDocument(Document doc) {
        currentDocument = doc;
    }

    public Document getCurrentDocument() {
        return currentDocument;
    }

    public void saveCurrentDocument() {
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

    public void createDocument(docType type) {

    }

    public void openDocument(File file) {
        factory.getDocument(type, file);
    }

    public void removeDocument(Document doc) {
        if (tabs.contains(doc)) {
            tabs.remove(doc);
        }
    }

    public void setDirectory(Folder folder) {
        currentDirectory = folder;
    }

    public Folder getDirectory(Folder folder) {
        return currentDirectory;
    }
}
