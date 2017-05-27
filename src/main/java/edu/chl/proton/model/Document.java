package edu.chl.proton.model;


import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-01.
 */
public class Document {

    private FileUtility file;
    private IDoc docType;

    Document(IDoc type){
        this.docType = type;
    }

    Document(IDoc type, File file){
        this.docType = type;
        this.file = (FileUtility) file;
    }

    protected boolean doesExist(){
        if(file != null){
            return true;
        }
        return false;
    }

    /**
     * @return the file
     */
    protected File getFile(){
        return this.file;
    }

    /**
     *  Gets the path to the file.
     * @return the path to the file
     */
    protected String getPath(){
        return file.getPath();
    }

    /**
     * sets the file
     * @param file
     */
    protected void setFile(File file){
        this.file = (FileUtility) file;
    }

    public List<String> getLines(){
        return docType.getLines();
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
        file.save(getLines());
    }


    protected boolean save() throws  IOException {
        try{
            file.save(getLines());
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
        return file.getDateForLastEdited();
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