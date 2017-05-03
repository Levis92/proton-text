package edu.chl.proton.model;

/**
 * @author Stina Werme
 * Created by stinawerme on 01/05/17.
 */
public abstract class FileSystemEntity {

    private String name;

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected String getPath() {
        return "Hej";
    }

    protected void setPath(String path) {

    }
}
