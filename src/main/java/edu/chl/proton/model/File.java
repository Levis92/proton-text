package edu.chl.proton.model;

/**
 * @author Stina Werme
 * Created by stinawerme on 01/05/17.
 */
public class File extends FileSystemEntity {

    public File(String name) {
        this.setName(name);

    }
    // What should be saved? Shouldn't this be in document?
    protected void save() {

    }
}
