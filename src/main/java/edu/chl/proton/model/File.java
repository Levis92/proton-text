package edu.chl.proton.model;

/**
 * @author Stina Werme
 * Created by stinawerme on 01/05/17.
 */
public class File extends FileSystemEntity {

    //private Document document;

    public File(String name) {
        this.setName(name);
    }

    public File(String name, Folder parentFolder) {
        this.setName(name);
        parentFolder.addFile(this);
    }

    //protected Document getDocument() {
    //    return this.document;
    //}

    //protected void setDocument(Document document) {
    //    this.document = document;
    //}

    // What should be saved? Shouldn't this be in document?
    protected void save(File file) {

    }

    protected boolean isSaved(File file) {
        return true;
    }
}
