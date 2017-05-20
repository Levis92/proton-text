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
    private TextPosture posture;
    private TextWeight weight;
    private int size;

    public FontStyle(String font, TextPosture posture, TextWeight weight, int size){
        this.font = font;
        this.posture=posture;
        this.size=size;
        this.weight=weight;
    }
    public FontStyle(){
    }

    protected void setFont(String font){ // enum as well?
        if(font == null){

        }
        //TODO: Make fonts
        if(font.equalsIgnoreCase("SANS")){
            // FontStyle.setFont(font);

        } else if(font.equalsIgnoreCase("ROMAN")){
            return;

        } else if (font.equalsIgnoreCase("NORMAL")){
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
            case NORMAL:
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
            case NORMAL:
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

    protected String getItalicStyle(String makeStyle){
        return "<i>"+makeStyle+"</i>";
    }
    protected String getItalicBoldStyle(String makeStyle){
        return "<b><i>"+makeStyle+"</i></b>";
    }
    protected String getHeadingStyle(String makeStyle){

        return "<b><span style=\"color:#007E73\">" + makeStyle + "</span></b>";
    }
    protected String getLinkStyle(String makeStyle){

        return "<span style=\"text-decoration:underline;color:blue\">"+makeStyle+"</span>";
    }
    protected String getListStyle(String makeStyle){

        return "<span style=\"color:#007E73\">" + makeStyle + "</span>";
    }
    protected String getBoldStyle(String makeStyle){

        return "<b>"+makeStyle+"</b>";
    }
    protected String getQuoteStyle(String makeStyle){

        return "<span style=\"color:#007E73\">" + makeStyle + "</span>";
    }
    protected String getCodeStyle(String makeStyle){

        return "<b>"+makeStyle+"</b>";
    }

}
