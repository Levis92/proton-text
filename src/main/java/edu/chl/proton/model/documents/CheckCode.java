package edu.chl.proton.model.documents;

import java.util.regex.*;

/**
 * Created by Mickaela on 2017-08-08.
 * Responsible for finding and formatting matches of the pattern group "code"
 */
public class CheckCode implements ICheckPattern {

    /**
     * Compiles a pattern and returns it. Throws
     * PatternSyntaxException if it fails.
     * @param pattern
     * @return the specified pattern
     */
    @Override
    public Pattern pattern(String pattern) {
        Pattern code;
        try{
            code = Pattern.compile("\\`{3}(?<text>[^\\`]*)\\`{3}");
        } catch(PatternSyntaxException ex){
            throw(ex);
        }
        if(pattern.equals("code")){
            return code;
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
        Matcher match = pattern("code").matcher(text);
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
     * @param text the string to be formatted
     * @param style the style to be applid to the string
     * @return the formatted string
     */
    @Override
    public String checkPattern(String text, FontStyle style) {
        String tmp = text;
        // check for code
        Matcher match = pattern("code").matcher(tmp);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getCodeStyle(match.group(0)));
        }
        match.appendTail(sb);
        tmp = sb.toString();

        return tmp;
    }
}
