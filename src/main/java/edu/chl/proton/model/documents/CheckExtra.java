package edu.chl.proton.model.documents;

import java.util.regex.*;

/**
 * Created by Mickaela on 2017-08-08.
 * Responsible for finding and formatting matches of the pattern group "extras".
 */
public class CheckExtra implements ICheckPattern {

    /**
     * Compiles a pattern and returns it. Throws
     * PatternSyntaxException if it fails.
     * @param pattern
     * @return the specified pattern
     */
    @Override
    public Pattern pattern(String pattern) {
        Pattern hr;
        try {
            hr = Pattern.compile("^(\\*{5})");
        } catch (PatternSyntaxException ex) {
            throw (ex);
        }
        if(pattern.equals("hr")){
            return hr;
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
        Matcher match = pattern("hr").matcher(text);
        StringBuffer sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<hr style=\"color:lightgrey;\" size=\"2px\" noshade>");
        }
        match.appendTail(sb);
        return sb.toString();
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

        // check for hr
        Matcher match = pattern("hr").matcher(tmp);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getBoldStyle(match.group(0))); // TODO SHOULD BE EXTRA
        }
        match.appendTail(sb);
        tmp = sb.toString();

        return tmp;
    }
}
