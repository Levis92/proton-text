/*
 * Proton Text - A Markdown text editor
 * Copyright (C) 2017  Anton Levholm, Ludvig Ekman, Mickaela Södergren
 * and Stina Werme
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.chl.proton.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-01.
 */
public class Markdown implements IDoc {

    private List<String> lines = new ArrayList<>();

    public Markdown(){
        //TODO?
    }

    public List<String> getLines(){
        return lines;
    }

    /**
     * Loops through the text and checks for markdown syntax in
     * one row at a time.
     * @return the list with the resulting HTML from the markdown text
     */
    public String getHTML(){
        String tmp = "";
        for(String str : lines){
            tmp = tmp + "<p style=\"width:100%; font-family: helvetica; color: #444\">" +checkForMarkdown(str) + "</p>";
        }
        return tmp;
    }

    /**
     * Calls on different methods to give the string
     * the appropriate styling
     * @param str
     * @return the formatted string
     */
    protected String checkForMarkdown(String str){
        String tmp = checkPosture(str);
        tmp = checkHeading(tmp);
        tmp = checkLink(tmp);
        tmp = checkList(tmp);
        tmp = checkCode(tmp);
        tmp = checkExtra(tmp);
        return tmp;
    }

    /**
     * Compiles a pattern and returns it. Throws
     * PatternSyntaxException if it fails.
     * @param str
     * @return the specified pattern
     */
    protected Pattern posturePattern(String str) {
        Pattern bold;
        Pattern italic;
        Pattern boldItalic;
        Pattern quote;

        try {
            boldItalic = Pattern.compile("(\\*{3})(\\b(?:(?!\\\\[*]).)*?\\b)(\\*{3})");
            bold = Pattern.compile("(?<!\\*)(\\*{2})(\\b(?:(?!\\\\[*]).)*?\\b)(\\*{2})(?!\\*)");
            italic = Pattern.compile("(?<!\\*)(\\*)(\\b(?:(?!\\\\[*]).)*?\\b)(\\*)(?!\\*)");
            quote = Pattern.compile("(?m)(^>)(?!>)((.*))"); // \\>(?<text>[^\\>]*)\\\r
        } catch (PatternSyntaxException ex) {
            throw (ex);
        }
        if(str.equals("bold")){
            return bold;
        } else if(str.equals("italic")){
            return italic;
        } else if(str.equals("boldItalic")){
            return boldItalic;
        } else if(str.equals("quote")){
            return quote;
        }
        return null;
    }

    /**
     * Adds styling for the preview window
     * @param str
     * @return the string with the proper HTML styling
     */
    protected String checkPosture(String str){

        // check for quotes
        Matcher match = posturePattern("quote").matcher(str);
        StringBuffer sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<blockquote style=\"border-left: 3px solid lightgrey; padding-left: 15px; color: #555\">$2</blockquote>");
        }
        match.appendTail(sb);
        String tmp = sb.toString();

        // Check for italic and bold
        match = posturePattern("boldItalic").matcher(tmp);
        sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, "<i><b>$2</b></i>");
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for bold
        match = posturePattern("bold").matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<b>$2</b>");
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for italic
        match = posturePattern("italic").matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<i>$2</i>");
        }
        match.appendTail(sb);
        tmp = sb.toString();
        return tmp;
    }

    /**
     * Adds styling for the editor window
     * @param str the string to be formatted
     * @param style the style to be applid to the string
     * @return the formatted string
     */
    protected String checkPosture(String str, FontStyle style){
        String tmp = str;
        // check for quotes
        Matcher match = posturePattern("quote").matcher(tmp);
        while (match.find()) {
            tmp = match.replaceAll(style.getQuoteStyle(match.group(0)));
        }

        // Check for italic and bold
        match = posturePattern("boldItalic").matcher(tmp);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getItalicBoldStyle(match.group(0)));
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for bold
        match = posturePattern("bold").matcher(tmp);
        sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getBoldStyle(match.group(0)));
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for italic
        match = posturePattern("italic").matcher(tmp);
        sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getItalicStyle(match.group(0)));
        }
        match.appendTail(sb);
        tmp = sb.toString();

        return tmp;
    }

    /**
     * Compiles a pattern and returns it. Throws
     * PatternSyntaxException if it fails.
     * @param str
     * @return the specified pattern
     */
    protected Pattern headingPattern(String str){
        Pattern h1;
        Pattern h2;
        Pattern h3;
        Pattern h4;
        Pattern h5;
        Pattern h6;

        try{
            h1 = Pattern.compile("(?m)(^#)(?!#)((.*))");
            h2 = Pattern.compile("(?m)(^#{2})(?!#)((.*))");
            h3 = Pattern.compile("(?m)(^#{3})(?!#)((.*))");
            h4 = Pattern.compile("(?m)(^#{4})(?!#)((.*))");
            h5 = Pattern.compile("(?m)(^#{5})(?!#)((.*))");
            h6 = Pattern.compile("(?m)(^#{6})(?!#)((.*))");
        } catch (PatternSyntaxException ex){
            throw(ex);
        }

        if(str.equals("h6")){
            return h6;
        } else if(str.equals("h5")){
            return h5;
        } else if(str.equals("h4")){
            return h4;
        } else if(str.equals("h3")){
            return h3;
        } else if(str.equals("h2")){
            return h2;
        } else if(str.equals("h1")){
            return h1;
        }
        return null;
    }

    /**
     * Adds styling for the preview window
     * @param str
     * @return the string with the proper HTML styling
     */
    protected String checkHeading(String str){
        // Check for h6
        Matcher match = headingPattern("h6").matcher(str);
        StringBuffer sb = new StringBuffer();
        if (match.find()) {
            match.appendReplacement(sb, "<h6 style=\"font-family: helvetica; color: #444; border-bottom: 1px solid lightgrey; padding-bottom: 0.2em\">$2</h6>");
            match.appendTail(sb);
            return sb.toString();
        }

        // Check for h5
        match = headingPattern("h5").matcher(str);
        sb = new StringBuffer();
        if(match.find()) {
            match.appendReplacement(sb, "<h5 style=\"font-family: helvetica; color: #444; border-bottom: 1px solid lightgrey; padding-bottom: 0.2em\">$2</h5>");
            match.appendTail(sb);
            return sb.toString();
        }

        // check for h4
        match = headingPattern("h4").matcher(str);
        sb = new StringBuffer();
        if (match.find()){
            match.appendReplacement(sb, "<h4 style=\"font-family: helvetica; color: #444; border-bottom: 1px solid lightgrey; padding-bottom: 0.2em\">$2</h4>");
            match.appendTail(sb);
            return sb.toString();
        }

        // check for h3
        match = headingPattern("h3").matcher(str);
        sb = new StringBuffer();
        if (match.find()){
            match.appendReplacement(sb, "<h3 style=\"font-family: helvetica; color: #444; border-bottom: 1px solid lightgrey; padding-bottom: 0.2em\">$2</h3>");
            match.appendTail(sb);
            return sb.toString();
        }

        // check for h2
        match = headingPattern("h2").matcher(str);
        sb = new StringBuffer();
        if (match.find()){
            match.appendReplacement(sb, "<h2 style=\"font-family: helvetica; color: #444; border-bottom: 1px solid lightgrey; padding-bottom: 0.2em\">$2</h2>");
            match.appendTail(sb);
            return sb.toString();
        }

        // check for h1
        match = headingPattern("h1").matcher(str);
        sb = new StringBuffer();
        if (match.find()){
            match.appendReplacement(sb, "<h1 style=\"font-family: helvetica; color: #444; border-bottom: 1px solid lightgrey; padding-bottom: 0.2em\">$2</h1>");
            match.appendTail(sb);
            return sb.toString();
        }
        // no matches found, return original string
        return str;
    }

    /**
     * Adds styling for the editor window
     * @param str the string to be formatted
     * @param style the style to be applid to the string
     * @return the formatted string
     */
    protected String checkHeading(String str, FontStyle style){
        String tmp = str;
        //check for heading 6
        Matcher match = headingPattern("h6").matcher(tmp);
        while(match.find()) {
            tmp = match.replaceAll(style.getHeadingStyle(match.group(0)));
        }
        // Check for h5
        match = headingPattern("h5").matcher(tmp);
        while(match.find()) {
            tmp = match.replaceAll(style.getHeadingStyle(match.group(0)));
        }

        // check for h4
        match = headingPattern("h4").matcher(tmp);
        while(match.find()) {
            tmp = match.replaceAll(style.getHeadingStyle(match.group(0)));
        }

        // check for h3
        match = headingPattern("h3").matcher(tmp);
        while(match.find()) {
            tmp = match.replaceAll(style.getHeadingStyle(match.group(0)));
        }

        // check for h2
        match = headingPattern("h2").matcher(tmp);
        while(match.find()) {
            tmp = match.replaceAll(style.getHeadingStyle(match.group(0)));
        }

        // check for h1
        match = headingPattern("h1").matcher(tmp);
        while(match.find()) {
            tmp = match.replaceAll(style.getHeadingStyle(match.group(0)));
        }
        return tmp;
    }

    /**
     * Compiles a pattern and returns it. Throws
     * PatternSyntaxException if it fails.
     * @param str
     * @return the specified pattern
     */
    protected Pattern linkPattern(String str){
        Pattern textLink;
        Pattern picLink;
        try{
            textLink = Pattern.compile("(?<!\\!)\\[(?<text>[^\\]]*)\\]\\((?<link>[^\\)]*)\\)");
            picLink = Pattern.compile("\\!\\[(?<text>[^\\]]*)\\]\\((?<link>[^\\)]*)\\)");
        } catch (PatternSyntaxException ex){
            throw(ex);
        }

        if(str.equals("textLink")){
            return textLink;
        } else if(str.equals("picLink")){
            return picLink;
        }
        return null;
    }

    /**
     * Adds styling for the preview window
     * @param str
     * @return the string with the proper HTML styling
     */
    protected String checkLink(String str){
        // Check for img link
        Matcher match = linkPattern("picLink").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, "<img style=\"max-width:100%\" src=\"$2\" alt=\"$1\">");
        }
        match.appendTail(sb);
        String tmp = sb.toString();

        // check for text link
        match = linkPattern("textLink").matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<a href=\"$2\">$1</a>");
        }
        match.appendTail(sb);
        tmp = sb.toString();
        return tmp;
    }

    /**
     * Adds styling for the editor window
     * @param str the string to be formatted
     * @param style the style to be applid to the string
     * @return the formatted string
     */
    protected String checkLink(String str, FontStyle style){
        String tmp = str;
        // Check for img link
        Matcher match = linkPattern("picLink").matcher(tmp);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getLinkStyle(match.group(0)));
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for text link
        match = linkPattern("textLink").matcher(tmp);
        sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getLinkStyle(match.group(0)));
        }
        match.appendTail(sb);
        tmp = sb.toString();

        return tmp;
    }

    /**
     * Compiles a pattern and returns it. Throws
     * PatternSyntaxException if it fails.
     * @param str
     * @return the specified pattern
     */
    protected Pattern listPattern(String str){
        Pattern unorderedList;
        Pattern orderedList;
        Pattern list;
        try{
            orderedList = Pattern.compile("^(\\d\\.\\u0020|\\d{2}\\.\\u0020)((.*))");
            unorderedList = Pattern.compile("^(\\*\\u0020|-\\u0020)((.*))");
        } catch (PatternSyntaxException ex){
            throw(ex);
        }
        if(str.equals("orderedList")){
            return orderedList;
        } else if(str.equals("unorderedList")){
            return unorderedList;
        }
        return null;
    }

    /**
     * Adds styling for the preview window
     * @param str
     * @return the string with the proper HTML styling
     */
    protected String checkList(String str){
        // Check for ordered list
        Matcher match = listPattern("orderedList").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            char nbr = str.charAt(match.regionStart());
            match.appendReplacement(sb, "<ol style=\"margin-bottom:2px; margin-top:2px;\" start=\"" + nbr + "\"><li>$2</li></ol>");
        }
        match.appendTail(sb);
        String tmp = sb.toString();

        // check for unordered list
        match = listPattern("unorderedList").matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<ul style=\"margin-bottom:2px; margin-top:2px;\"><li>$2</li></ul>");
        }
        match.appendTail(sb);
        tmp = sb.toString();
        return tmp;
    }

    /**
     * Adds styling for the editor window
     * @param str the string to be formatted
     * @param style the style to be applid to the string
     * @return the formatted string
     */
    protected String checkList(String str, FontStyle style){
        String tmp = str;

        // Check for ordered list
        Matcher match = listPattern("orderedList").matcher(tmp);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getListStyle(match.group(0)));
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for unordered list
        match = listPattern("unorderedList").matcher(tmp);
        sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getListStyle(match.group(0)));
        }
        match.appendTail(sb);
        tmp = sb.toString();

        return tmp;
    }

    /**
     * Compiles a pattern and returns it. Throws
     * PatternSyntaxException if it fails.
     * @param str
     * @return the specified pattern
     */
    protected Pattern codePattern(String str){
        Pattern code;
        try{
            code = Pattern.compile("\\`{3}(?<text>[^\\`]*)\\`{3}");
        } catch(PatternSyntaxException ex){
            throw(ex);
        }
        if(str.equals("code")){
            return code;
        }
        return null;
    }

    /**
     * Adds styling for the preview window
     * @param str
     * @return the string with the proper HTML styling
     */
    protected String checkCode(String str){
        Matcher match = codePattern("code").matcher(str);
        StringBuffer sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<div style=\"background-color: #f8f8f8; border: 1px solid #ddd; " +
                    "font-size: 13px; line-height: 19px; overflow: auto; padding: 6px 10px; border-radius: 3px; " +
                    "margin: 15px\"><code style=\"font-family: monospace\">$1</code></div>");
        }
        match.appendTail(sb);
        return sb.toString();
    }

    /**
     * Adds styling for the editor window
     * @param str the string to be formatted
     * @param style the style to be applid to the string
     * @return the formatted string
     */
    protected String checkCode(String str, FontStyle style){
        String tmp = str;
        // check for code
        Matcher match = codePattern("code").matcher(tmp);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getBoldStyle(match.group(0))); // TODO SHOULD BE CODE
        }
        match.appendTail(sb);
        tmp = sb.toString();

        return tmp;
    }

    /**
     * Compiles a pattern and returns it. Throws
     * PatternSyntaxException if it fails.
     * @param str
     * @return the specified pattern
     */
    protected Pattern extraPattern(String str){
        Pattern hr;
        try {
            hr = Pattern.compile("^(\\*{5})");
        } catch (PatternSyntaxException ex) {
            throw (ex);
        }
        if(str.equals("hr")){
            return hr;
        }
        return null;
    }

    /**
     * Adds styling for the preview window
     * @param str
     * @return the string with the proper HTML styling
     */
    protected String checkExtra(String str){
        Matcher match = extraPattern("hr").matcher(str);
        StringBuffer sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<hr style=\"color:lightgrey;\" size=\"2px\" noshade>");
        }
        match.appendTail(sb);
        return sb.toString();
    }

    /**
     * Adds styling for the editor window
     * @param str the string to be formatted
     * @param style the style to be applid to the string
     * @return the formatted string
     */
    protected String checkExtra(String str, FontStyle style){
        String tmp = str;

        // check for hr
        Matcher match = extraPattern("hr").matcher(tmp);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getBoldStyle(match.group(0))); // TODO SHOULD BE EXTRA
        }
        match.appendTail(sb);
        tmp = sb.toString();

        return tmp;
    }

    /**
     * Searches for specific markdown syntax pattern.
     * When match is found, calls on different style methods
     * that returns the matched string with HTML styling
     * @return the list with HTML styling
     */
    public String getText(){
        FontStyle style = new FontStyle();
        List<String> text = new ArrayList<>();

        String wholeText = "";
        String tmp;

        for(String str : lines) {

            tmp = checkPosture(str, style);
            tmp = checkHeading(tmp, style);
            tmp = checkLink(tmp, style);
            tmp = checkList(tmp, style);
            tmp = checkCode(tmp, style);
            tmp = checkExtra(tmp, style);

            text.add(tmp);
        }

        for(String s : text){
            wholeText = wholeText + "<p>" + s + "</p>";
        }
        wholeText = "<html dir=\"ltr\"><head></head><body contenteditable=\"true\"><p><font face=\"Segoe UI\">" + wholeText + "</font></p></body></html>";

        return wholeText;
    }

    /**
     * Takes every row in the text and wraps it with basic HTML tags.
     * @param str the text, where every item is a row
     * @return returns the whole text as a string with HTML tags
     * wrapped around it.
     */
    public void setText(List<String> str){
        this.lines = str;
    }
}