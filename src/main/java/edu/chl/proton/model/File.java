package edu.chl.proton.model;

import java.io.*;
import java.util.List;

/**
 * @author Stina Werme
 * Created by stinawerme on 01/05/17.
 */
public class File extends FileSystemEntity {

    private boolean isSaved;


    public File(String name) {
        this.setName(name);
    }

    public File(String name, Folder parentFolder) {
        this.setName(name);
        parentFolder.addFile(this);
    }

    protected void setIsSaved(boolean state) {
        isSaved = state;
    }

    protected boolean isSaved() {
        return isSaved;
    }

    protected void save(List<String> text) throws IOException {

        File file = new File(this.getPath());
        BufferedWriter out = new BufferedWriter(new FileWriter(String.valueOf(file)));

        for(String line : text) {
            out.write(line);
        }
        out.close();
        setIsSaved(true);
    }

    // TODO
    protected String lastEdited() {
        return "";
    }

    // TODO
    protected void remove() {

    }

    // Aqcuires the text from the file we opened.
    protected void aqcuireText(){

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(file.getName());

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            // Close file.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            file.getName() + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + file.getName() + "'");
        }


    }

}
