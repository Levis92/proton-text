package edu.chl.proton.model;


import java.io.IOException;
import java.util.*;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-01.
 */
public class Document {

    private Cursor cursor;
    private File file;
    private List<String> lines = new ArrayList<String>();

    IDoc docType;

    public Document(IDoc type){
        this.docType = type;
    }

    public Document(IDoc type, String path){
        this.docType = type;
        file = new File(path);
    }

    /**
     * @return the cursor
     */
    protected Cursor getCursor(){
        return this.cursor;
    }

    /**
     * sets the cursor
     * @param cursor
     */
    protected void setCursor(Cursor cursor){
        this.cursor = cursor;
    }

    /**
     * @return the file
     */
    protected File getFile(){
        return this.file;
    }

    /**
     * sets the file
     * @param file
     */
    protected void setFile(File file){
        this.file = file;
    }

    /**
     * Adds the file to the search path.
     * @param path
     */
    protected void addFile(String path){
        file.setPath(path);
        // setFile(rootFolder.getFileFromPath(path)); ???
    }

    /**
     * Inserts a string at the cursor's position and
     * moves the cursor x step forward, where x is
     * the length of the string.
     * @param str
     */
    protected void insertPart(String str){
        int row = cursor.getPosition().getY();
        int col = cursor.getPosition().getX();

        String tmp = lines.get(row);

        StringBuilder sb = new StringBuilder(tmp);
        sb.insert(col, str);

        lines.set(row, sb.toString());
        cursor.setPosition(row, col + str.length());
    }

    /**
     * adds a row at the end of the text
     * @param lines
     */
    protected void addLines(String lines){
        this.lines.add(lines);
    }

    /**
     * Removes one row in the document
     * @param index
     */
    protected void removeLines(int index){
        lines.remove(index);
    }

    /**
     * Removes all text in the document.
     */
    protected void removeAllLines(){
        lines.clear();
    }

    /**
     * Inserts a char at the cursor's position and
     * moves the cursor one step forward. If Enter was
     * the key pressed, moves the cursor one row down.
     * @param ch
     */
    protected void insertChar(char ch){

        int row = cursor.getPosition().getY();
        int col = cursor.getPosition().getX();

        // check if Enter was the key pressed
        if(ch == '\r'){
            cursor.setPosition(row + 1, col);
        } else {
            String tmp = lines.get(row);

            StringBuilder sb = new StringBuilder(tmp);
            sb.insert(col, ch);

            lines.set(row, sb.toString());
            cursor.setPosition(row, col + 1);
        }

    }

    /**
     * Deletes the char at the cursor's position and
     * moves the cursor one step back.
     */
    protected void deleteChar(){
        int row = cursor.getPosition().getY();
        int col = cursor.getPosition().getX();

        String tmp = lines.get(row);
        StringBuilder sb = new StringBuilder(tmp);
        sb.deleteCharAt(col);

        lines.set(row, sb.toString());
        cursor.setPosition(row, col - 1);
    }

    /**
     * Calls on the appropriate class for getText
     * @returna list of the text
     */
    public String getText(){
        return docType.getText();
    }

    /**
     * Calls on the appropriate class for setText
     * @param text
     */
    protected void setText(List<String> text){
        docType.setText(text);
    }

    /**
     * Saves the text in the file.
     * @throws IOException
     */
    protected void save() throws IOException{
        file.save(lines);
    }

    /**
     * removes the file.
     */
    protected void remove(){
        file.remove();
    }

    /**
     * Checks if the file is saved
     * @return true or false
     */
    protected boolean isSaved(){
        return file.isSaved();
    }

    /**
     * Aqcuires the text from the opened file.
     */
    protected void aqcuireText() {
        file.aqcuireText();
    }

}