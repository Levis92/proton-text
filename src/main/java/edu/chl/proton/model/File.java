package edu.chl.proton.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.text.SimpleDateFormat;


/**
 * @author Stina Werme
 * Created by stinawerme on 01/05/17.
 */
public class File extends FileSystemEntity {

    private boolean isSaved;


    public File(String name) {
        this.setName(name);
    }

    protected void setIsSaved(boolean state) {
        isSaved = state;
    }

    protected boolean isSaved() {
        return isSaved;
    }

    protected void save(List<String> text) throws IOException {
        try {

            java.io.File file = new java.io.File(this.getPath());
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            System.out.println("hej");
            for(String line : text) {
                out.write(line);
                out.newLine();
            }
            out.close();
            setIsSaved(true);
        } catch (IOException e) {

        }
    }

    // TODO
    protected String lastEdited() {

        File file = new File("C:\\Myfile.txt");

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        return "sdf.format(file.lastModified())";
    }

    protected void remove() {
        this.getFile().delete();
    }
}