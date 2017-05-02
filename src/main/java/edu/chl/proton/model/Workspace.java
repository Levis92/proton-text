package edu.chl.proton.model;

import com.sun.tools.javac.code.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 */
public class Workspace {
    private static Workspace uniqueInstance = new Workspace();
    private List<Document> tabs = new ArrayList<Document>();
    private Document currentDocument;
    private Folder currentDirectory;
    private DocumentFactory factory = new DocumentFactory();
    private enum docType {MARKDOWN, PLAIN};

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

    public void setCurrentDirectory(Folder folder) {
        currentDirectory = folder;
    }

    public Folder getCurrentDirectory() {
        return currentDirectory;
    }

    public void createDocument(docType type, File file) {
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

    public Folder getDirectory(Folder) {
        return currentDirectory;
    }
}
