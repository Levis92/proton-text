package edu.chl.proton.model;

import java.util.ArrayList;

/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-05-01.
 */
public class Workspace {
    private static Workspace uniqueInstance = new Workspace();
    /* List<Document> tabs = new ArrayList<Document>();
    Document currentDocument;
    Folder currentDirectory;
    DocumentFactory factory; */

    private Workspace() {}

    public static Workspace getInstance() {
        return uniqueInstance;
    }
/*
    public void setCurrentDocument(Document doc) {
        currentDocument = doc;
    }

    public Document getCurrentDocument() {
        return currentDocument;
    }

    public setCurrentDirectory(Folder folder) {
        currentDirectory = folder;
    }

    public Folder getCurrentDirectory() {
        return currentDirectory;
    }

    public void createDocument(File file) {

    }

    public void removeDocument(File file) {

    }

    public void setDirectory(Folder) {

    }

    public Folder getDirectory(Folder) {

    }
*/
}
