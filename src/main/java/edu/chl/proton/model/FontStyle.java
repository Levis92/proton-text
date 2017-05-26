package edu.chl.proton.model;

/**
 * @author Ludvig Ekman
 * Created by ludvig on 2017-05-01.
 */
// TODO: Take in a Part to change, or have a public CurrentPart? Or otherwise solve problem with referencing to a
// non-static object from a static method.
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
