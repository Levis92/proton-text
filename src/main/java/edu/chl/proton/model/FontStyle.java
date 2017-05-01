package edu.chl.proton.model;

import static java.awt.Font.BOLD;
import static java.awt.Font.ITALIC;
import static java.time.format.SignStyle.NORMAL;

/**
 * @author Ludvig Ekman
 * Created by ludvig on 2017-05-01.
 */
public class FontStyle {
    private String font;
    private enum Posture{
        BOLD, ITALIC, BOLDANDITALIC, NORMAL;
    }
    Posture posture;
    private int size;

    public FontStyle(String font, Posture posture, int size){
        this.font = font;
        this.posture=posture;
        this.size=size;
    }

    protected void setFont(String font){ // enum as well?
        if(font == null){

        }
        //TODO: Make fonts
        if(font.equalsIgnoreCase("SANS")){
            return; //set SANS

        } else if(font.equalsIgnoreCase("SLIDE")){
            return;

        } else if (font.equalsIgnoreCase("ASSIGNMENT")){
            return; // default
        }
    }

    protected String getFont(){
        return font;
    }

    protected void setPosture(Posture posture) {
        switch (posture) {
            case BOLD:
                // Do stuff bold
                break;

            case ITALIC:
                // Make stuff italic
                break;

            case BOLDANDITALIC:
                // and so on
                break;

            default:
                // reset / do nothing?
                break;
        }
    }

    protected Posture getPosture(){
        return posture;
    }

    protected void setSize(int size){
            // do something with the text size
    }

    protected int getSize() {
        return size;
    }
}
