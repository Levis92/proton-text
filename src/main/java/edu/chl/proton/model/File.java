package edu.chl.proton.model;

/**
 * @author Stina Werme
 * Created by stinawerme on 01/05/17.
 */
public class File extends FileSystemEntity {

    private Document document;

    public File(String name, Document document) {
        this.setName(name);
        this.document = document;
    }

    public File(String name, Document document, Folder parentFolder) {
        this(name, document);
        parentFolder.addFile(this);
    }

    protected Document getDocument() {
        return this.document;
    }

    protected void setDocument(Document document) {
        this.document = document;
    }

    // What should be saved? Shouldn't this be in document?
    protected void save() {

    }
}
