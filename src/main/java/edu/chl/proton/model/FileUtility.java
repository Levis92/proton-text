package edu.chl.proton.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;


/**
 * @author Stina Werme
 * Created by stinawerme on 01/05/17.
 */
public class FileUtility extends File {

    private File file;
    private boolean isSaved;


    public FileUtility(String name, String path) {
        super(path);
        this.file = new File(name);
        this.setName(name);
        this.setPath(path);
    }

    public FileUtility(String path) {
        super(path);
        this.file = new File(path);
        this.setPath(path);
    }

    protected void setName(String name) {
        File file = new File(this.file.getParentFile(), name);
        this.file.renameTo(file);
    }

    protected void setPath(String path) {
        File file = new File(path);
        this.file.renameTo(file);
    }

    protected File getFile() {
        return this.file;
    }

    protected void setIsSaved(boolean state) {
        isSaved = state;
    }

    protected boolean isSaved() {
        return isSaved;
    }

    /**
     * Save file
     * @param text List<String>
     * @throws IOException
     */
    protected void save(List<String> text) throws IOException {
        try {

            File file = new File(this.getPath());
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for(String line : text) {
                out.write(line);
                out.newLine();
            }
            out.close();
            setIsSaved(true);
        } catch (IOException e) {

        }
    }

    /**
     * Returns date and time for last time edited
     * @return String
     */
    protected String getDateForlastEdited() {

        File file = new File(getName());

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        return sdf.format(file.lastModified());
    }

    /**
     * Get last edited file
     * @param dirPath String
     * @return File
     */
    private File getLastEditedFile(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }
        return lastModifiedFile;
    }

    /**
     * Delete file
     */
    protected void remove() {
        this.getFile().delete();
    }

    /**
     * Aqcuires the text from the opened file.
     * @return List<String>
     */
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

