package edu.chl.proton.model;

/**
 * @author Stina Werme
 * Created by stinawerme on 01/05/17.
 */
public abstract class FileSystemEntity {

    private String name;
    private String path;

    private Folder parentFolder;


    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected String getPath() {
        if (this.parentFolder != null) {
            return this.parentFolder.getPath() + "/" + this.name;
        } else {
            return "/" + this.name;
            //return "/Users/stinawerme/Desktop/test-mapp/hej.txt";
        }
    }

    protected void setPath(String name) {
        path = this.parentFolder.getPath() + "/" + this.name;
    }

    protected Folder getParentFolder() {
        return this.parentFolder;
    }

    protected void setParentFolder(Folder folder) {
        this.parentFolder = folder;
    }
}