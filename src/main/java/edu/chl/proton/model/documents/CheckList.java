package edu.chl.proton.model.documents;

import java.util.regex.*;

/**
 * Created by Mickaela on 2017-08-08.
 * Responsible for finding and formatting matches of the pattern group "Lists".
 */
public class CheckList implements ICheckPattern{

    /**
     * Compiles a pattern and returns it. Throws
     * PatternSyntaxException if it fails.
     * @param pattern
     * @return the specified pattern
     */
    @Override
    public Pattern pattern(String pattern) {
        Pattern unorderedList;
        Pattern orderedList;
        try{
            orderedList = Pattern.compile("^(\\d\\.\\u0020|\\d{2}\\.\\u0020)((.*))");
            unorderedList = Pattern.compile("^(\\*\\u0020|-\\u0020)((.*))");
        } catch (PatternSyntaxException ex){
            throw(ex);
        }
        if(pattern.equals("orderedList")){
            return orderedList;
        } else if(pattern.equals("unorderedList")){
            return unorderedList;
        }
        return null;
    }

    /**
     * Adds styling for the preview window
     * @param text
     * @return the string with the proper HTML styling
     */
    @Override
    public String checkPattern(String text) {
        // Check for ordered list
        Matcher match = pattern("orderedList").matcher(text);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            char nbr = text.charAt(match.regionStart());
            match.appendReplacement(sb, "<ol style=\"margin-bottom:2px; margin-top:2px;\" start=\"" + nbr + "\"><li>$2</li></ol>");
        }
        match.appendTail(sb);
        String tmp = sb.toString();

        // check for unordered list
        match = pattern("unorderedList").matcher(tmp);
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
     * @param text the string to be formatted
     * @param style the style to be applid to the string
     * @return the formatted string
     */
    @Override
    public String checkPattern(String text, FontStyle style) {
        String tmp = text;

        // Check for ordered list
        Matcher match = pattern("orderedList").matcher(tmp);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getListStyle(match.group(0)));
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for unordered list
        match = pattern("unorderedList").matcher(tmp);
        sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getListStyle(match.group(0)));
        }
        match.appendTail(sb);
        tmp = sb.toString();

        return tmp;
    }
}
