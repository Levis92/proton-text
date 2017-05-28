package edu.chl.proton.model;

/**
 * @author Ludvig Ekman
 * Created by ludvig on 2017-05-01.
 * Each method returns the given text with a html code around it,
 * marking that the text has a style of some sort.
 * Used in html-view.
 */
public class FontStyle {
    FontStyle(){
    }
    String getItalicStyle(String makeStyle){

        return "<i>"+makeStyle+"</i>";
    }
    String getItalicBoldStyle(String makeStyle){

        return "<b><i>"+makeStyle+"</i></b>";
    }
    String getHeadingStyle(String makeStyle){

        return "<b><span style=\"color:#007E73\">" + makeStyle + "</span></b>";
    }
    String getLinkStyle(String makeStyle){

        return "<span style=\"text-decoration:underline;color:blue\">"+makeStyle+"</span>";
    }
    String getListStyle(String makeStyle){

        return "<span style=\"color:#007E73\">" + makeStyle + "</span>";
    }
    String getBoldStyle(String makeStyle){

        return "<b>"+makeStyle+"</b>";
    }
    String getQuoteStyle(String makeStyle){

        return "<span style=\"color:#007E73\">" + makeStyle + "</span>";
    }
    String getCodeStyle(String makeStyle){

        return "<b>"+makeStyle+"</b>";
    }
}
