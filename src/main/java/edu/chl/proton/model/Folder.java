package edu.chl.proton.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stina Werme
 * Created by stinawerme on 01/05/17.
 */
public class Folder extends FileSystemEntity {

    private List<Folder> folders = new ArrayList<Folder>();
    private List<File> files = new ArrayList<File>();


    public Folder(String name) {
        this.setName(name);

    }

    protected void move(Folder folder) {
        folders.add(folder);
    }

    protected void move(File file) {
        files.add(file);
    }

    protected void add() {

    }

    protected List<Folder> getFolders() {
        return folders;
    }

    protected List<File> getFiles() {
        return files;
    }

}
