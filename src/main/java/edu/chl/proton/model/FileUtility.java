package edu.chl.proton.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;


/**
 * @author Stina Werme
 * Created by stinawerme on 01/05/17.
 */
public class FileUtility {

    private java.io.File file;
    private boolean isSaved;


    public FileUtility(String name, String path) {
        this.file = new File(name);
        this.setName(name);
        this.setPath(path);
    }
    public FileUtility(String path) {
        this.file = new File(path);
        this.setPath(path);
    }

    protected String getName() {
        return this.file.getName();
    }

    protected void setName(String name) {
        java.io.File file = new java.io.File(this.file.getParentFile(), name);
        this.file.renameTo(file);
    }

    protected String getPath() {
        return this.file.getPath();
    }

    protected void setPath(String path) {
        java.io.File file = new java.io.File(path);
        this.file.renameTo(file);
    }

    protected java.io.File getFile() {
        return this.file;
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

    protected String getDateForlastEdited() {

        java.io.File file = new java.io.File(getName());

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        return sdf.format(file.lastModified());
    }

    private java.io.File getLastEditedFile(String dirPath) {
        java.io.File dir = new java.io.File(dirPath);
        java.io.File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        java.io.File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }
        return lastModifiedFile;
    }

    protected void remove() {
        this.getFile().delete();
    }

    // Aqcuires the text from the file we opened.
    protected List<String> aqcuireText(){

        // This will reference one line at a time
        String line;
        List<String> lines = new ArrayList<>();

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(getName());

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
                            getName() + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + getName() + "'");
        }

        return lines;
    }

}

