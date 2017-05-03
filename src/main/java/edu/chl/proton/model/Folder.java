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

    protected void addFolder(Folder folder) {
        folders.add(folder);
    }

    protected void addFile(File file) {
        files.add(file);
    }

    protected List<Folder> getFolders() {
        return childFolders;
    }

    protected List<File> getFiles() {
        return files;
    }

    protected void removeFolder(Folder folder) {
        childFolders.remove(folder);
    }

    protected void removeFile(File file) {
        files.remove(file);

    }
}
