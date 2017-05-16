package edu.chl.proton.model;

import java.io.File;

/**
 * @author Stina Werme
 * Created by stinawerme on 01/05/17.
 */
public abstract class FileSystemEntity {

    private java.io.File file;

    private Folder parentFolder;

    protected String getName() {
        return this.file.getName();
    }

    protected void setName(String name) {
        File file = new File(this.file.getParentFile(), name);
        this.file.renameTo(file);
    }

    protected String getPath() {
       return this.file.getPath();
    }

    protected void setPath(String path) {
        File file = new File(path);
        this.file.renameTo(file);
    }

    protected File getFile() {
        return this.file;
    }



    protected void setParentFolder(Folder folder) {
        this.parentFolder = folder;
    }

    protected Folder getParentFolder() {
        return this.parentFolder;
    }
}