/*
 * Proton Text - A Markdown text editor
 * Copyright (C) 2017  Anton Levholm, Ludvig Ekman, Mickaela Södergren
 * and Stina Werme
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.chl.proton.model.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;


/**
 * @author Stina Werme
 * Created by stinawerme on 01/05/17.
 *
 * Extends Java's own file class and adds more functionality to a file.
 */
public class FileUtility extends File {

    private boolean isSaved;
    
    public FileUtility(String path) {
        super(path);
    }

    protected File getFile() {
        return this;
    }

    /**
     * Change isSaved to true if saved or false if not saved.
     * @param state true or false.
     */
    protected void setIsSaved(boolean state) {
        isSaved = state;
    }

    /**
     * @return true if saved and false if not saved.
     */
    public boolean isSaved() {
        return isSaved;
    }

    /**
     * Save file
     * @param text List<String>
     * @throws IOException
     */
    public void save(List<String> text) throws IOException {
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
     * Returns date and time for the last time the file was modified.
     * @return String
     */
    public String getDateForLastEdited() {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        return sdf.format(this.lastModified());
    }

    /**
     * Get last edited file.
     * @param dirPath String
     * @return the last edited file.
     */
    protected File getLastEditedFile(String dirPath) {
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
     * Delete file.
     */
    public void remove() {
        this.getFile().delete();
    }

    /**
     * Aqcuires the text from the opened file.
     * @return List<String>
     */
    public List<String> aqcuireText(){

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

