package edu.chl.proton.model;


import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-01.
 */
public class Document {

    private Cursor cursor;
    private FileUtility file;
    private List<String> lines = new ArrayList<String>();

    IDoc docType;

    public Document(IDoc type){
        this.docType = type;
    }

    public Document(IDoc type, File file){
        this.docType = type;
        this.file = (FileUtility) file;
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
        this.file = (FileUtility) file;
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

    public String getHTML(){
        return docType.getHTML();
    }

    /**
     * Saves the text in the file.
     * @throws IOException
     */
    protected void save(String path) throws IOException{
        file = new FileUtility(path);
        file.save(lines);
    }


    protected boolean save() throws  IOException {
        try{
            file.save(lines);
            return true;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    /**
     * removes the file.
     */
    protected void remove(){
        file.remove();
    }

    /**
     * Returns the date of the last time the file was editet.
     * @return string with the date of last edit
     */
    protected String getDateForLastEdited(){
        return file.getDateForlastEdited();
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