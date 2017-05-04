package edu.chl.proton.model;

import static java.awt.Font.BOLD;
import static java.awt.Font.ITALIC;
import static java.time.format.SignStyle.NORMAL;

/**
 * @author Ludvig Ekman
 * Created by ludvig on 2017-05-01.
 */
// TODO: Take in a Part to change, or have a public CurrentPart? Or otherwise solve problem with referencing to a
// non-static object from a static method.
public class FontStyle {
    private String font;
    TextPosture posture;
    TextWeight weight;
    private int size;

    public FontStyle(String font, TextPosture posture, TextWeight weight, int size){
        this.font = font;
        this.posture=posture;
        this.size=size;
        this.weight=weight;
    }

    protected void setFont(String font){ // enum as well?
        if(font == null){

        }
        //TODO: Make fonts
        if(font.equalsIgnoreCase("SANS")){
            // FontStyle.setFont(font);

        } else if(font.equalsIgnoreCase("SLIDE")){
            return;

        } else if (font.equalsIgnoreCase("ASSIGNMENT")){
            return; // default
        }
    }

    protected String getFont(){
        return font;
    }

    protected void setTextPosture(TextPosture posture) {
        switch (posture) {
            case ITALIC:
                // Make stuff italic
                break;

            case :
                // and so on
                break;

            default:
                // reset / do nothing?
                break;
        }
    }
    protected TextPosture getTextPosture(){
        return posture;
    }
    protected void setTextWeight(TextWeight weight) {
        switch (weight) {
            case BOLD:
                // Do stuff bold
                break;
            case :
            // and so on
            break;

            default:
                //text3.setFont(Font.font(family, FontPosture.ITALIC, size));
                break;
        }
    }
    protected TextWeight getTextWeight(){
        return weight;
    }


    protected void setSize(int size){
            // do something with the text size
    }

    protected int getSize() {
        return size;
    }
}
