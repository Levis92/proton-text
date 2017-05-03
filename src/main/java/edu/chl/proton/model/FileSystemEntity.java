package edu.chl.proton.model;

/**
 * @author Stina Werme
 * Created by stinawerme on 01/05/17.
 */
public abstract class FileSystemEntity {

    private String name;

    private Folder parentFolder;


    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected String getPath() {
    protected void setPath(String path) {
        if (this.parentFolder != null) {
            return this.parentFolder.getPath() + "/" + this.name;
        } else {
            return "/" + this.name;
        }
    }

    protected Folder getParentFolder() {
        return this.parentFolder;
    }

    protected void setParentFolder(Folder folder) {
        this.parentFolder = folder;
    }
}
