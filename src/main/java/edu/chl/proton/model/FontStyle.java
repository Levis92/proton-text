package edu.chl.proton.model;

/**
 * @author Ludvig Ekman
 * Created by ludvig on 2017-05-01.
 * Each method returns the given text with a html code around it,
 * marking that the text has a style of some sort.
 * Used in html-view.
 */
public class FontStyle {
    public FontStyle(){
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
