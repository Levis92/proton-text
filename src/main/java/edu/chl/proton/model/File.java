package edu.chl.proton.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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

    protected void save(List<String> text) throws IOException {

        File file = new File(this.getPath());
        BufferedWriter out = new BufferedWriter(new FileWriter(String.valueOf(file)));

        for(String line : text) {
            out.write(line);
        }
        out.close();

    }

    protected boolean isSaved() {
        return true;
    }
}
