package edu.chl.proton.model;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-01.
 */
public abstract class Document {

    private Cursor cursor;;
    private File file;
    private List<String> lines = new ArrayList<String>();
    private List<Parts> parts = new ArrayList<Parts>();

    protected Cursor getCursor(){
        return this.cursor;
    }

    protected void setCursor(Cursor cursor){
        this.cursor = cursor;
    }

    protected File getFile(){
        return this.file;
    }

    protected void setFile(File file){
        this.file = file;
    }

    protected void addFile(String path){
        // file.setPath(path);
        // setFile(rootFolder.getFileFromPath(path)); ???
    }

    protected void addParts(Parts parts){
        this.parts.add(parts);
    }

    protected void removeParts(int index){
        parts.remove(index);
    }

    protected void removeAllParts(){
        parts.clear();
    }

    protected void addLines(String lines){
        this.lines.add(lines);
    }

    protected void removeLines(int index){
        lines.remove(index);
    }

    protected void removeAllLines(){
        lines.clear();
    }

    protected void setText(List<String> text){
        for(String str : text){
            lines.add(str);
        }
    }

    // Document needs a getText() method that returns a formatted List, where every list item is a row in the document and every character gets a style.

    protected List<String> getText(){
        return null;
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
