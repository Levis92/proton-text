package edu.chl.proton.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stina Werme
 * Created by stinawerme on 01/05/17.
 */
public class Folder extends FileSystemEntity {

    private List<Folder> childFolders = new ArrayList<Folder>();
    private List<File> files = new ArrayList<File>();


    public Folder(String name) {
        this.setName(name);
    }

    public Folder(String name, Folder parentFolder) {
        this.setName(name);
        parentFolder.addFolder(this);
    }

    protected List<Folder> getFolders() {
        return childFolders;
    }

    protected List<File> getFiles() {
        return files;
    }

    protected void addFolder(Folder folder) {
        // Remove the given folder from its current folder
        folder.getParentFolder().removeFolder(folder);
        // Set the new parent folder
        folder.setParentFolder(this);
        // Add it to its new parent folder's child folder list
        childFolders.add(folder);
    }

    protected void addFile(File file) {
        // Remove the given file from its current folder
        file.getParentFolder().removeFile(file);
        // Set the new parent folder
        file.setParentFolder(this);
        // Add it to its new parent folder's file list
        files.add(file);
    }


    protected void removeFolder(Folder folder) {
        childFolders.remove(folder);
        folder.setParentFolder(null);
    }

    protected void removeFile(File file) {
        files.remove(file);
        file.setParentFolder(null);

    }


    // TODO
    protected File getFileFromPath(String path) {
        return null;
    }
}
