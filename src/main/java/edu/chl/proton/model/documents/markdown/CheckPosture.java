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

package edu.chl.proton.model.documents.markdown;

import edu.chl.proton.model.util.FontStyle;

import java.util.regex.*;

/**
 * Created by Mickaela on 2017-08-08.
 * Responsible for finding and formatting matches of the pattern group "Postures".
 */
public class CheckPosture implements ICheckPattern {

    /**
     * Compiles a pattern and returns it. Throws
     * PatternSyntaxException if it fails.
     * @param pattern
     * @return the specified pattern
     */
    @Override
    public Pattern pattern(String pattern){
        Pattern bold;
        Pattern italic;
        Pattern boldItalic;
        Pattern quote;

        try {
            boldItalic = Pattern.compile("(\\*{3})(\\b(?:(?!\\\\[*]).)*?\\b)(\\*{3})");
            bold = Pattern.compile("(?<!\\*)(\\*{2})(\\b(?:(?!\\\\[*]).)*?\\b)(\\*{2})(?!\\*)");
            italic = Pattern.compile("(?<!\\*)(\\*)(\\b(?:(?!\\\\[*]).)*?\\b)(\\*)(?!\\*)");
            quote = Pattern.compile("(?m)(^>)(?!>)((.*))");
        } catch (PatternSyntaxException ex) {
            throw (ex);
        }
        if(pattern.equals("bold")){
            return bold;
        } else if(pattern.equals("italic")){
            return italic;
        } else if(pattern.equals("boldItalic")){
            return boldItalic;
        } else if(pattern.equals("quote")){
            return quote;
        }
        return null;
    }

    /**
     * Adds styling for the preview window
     * @param text
     * @return the string with the proper HTML styling
     */
    @Override
    public String checkPattern(String text){
        // check for quotes
        Matcher match = pattern("quote").matcher(text);
        StringBuffer sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<blockquote style=\"border-left: 3px solid lightgrey; padding-left: 15px; color: #555\">$2</blockquote>");
        }
        match.appendTail(sb);
        String tmp = sb.toString();

        // Check for italic and bold
        match = pattern("boldItalic").matcher(tmp);
        sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, "<i><b>$2</b></i>");
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for bold
        match = pattern("bold").matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<b>$2</b>");
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for italic
        match = pattern("italic").matcher(tmp);
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
     * @param text the string to be formatted
     * @param style the style to be applid to the string
     * @return the formatted string
     */
    @Override
    public String checkPattern(String text, FontStyle style){
        String tmp = text;

        // check for quotes
        Matcher match = pattern("quote").matcher(tmp);
        while (match.find()) {
            tmp = match.replaceAll(style.getQuoteStyle(match.group(0)));
        }

        // Check for italic and bold
        match = pattern("boldItalic").matcher(tmp);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getItalicBoldStyle(match.group(0)));
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for bold
        match = pattern("bold").matcher(tmp);
        sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getBoldStyle(match.group(0)));
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for italic
        match = pattern("italic").matcher(tmp);
        sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getItalicStyle(match.group(0)));
        }
        match.appendTail(sb);
        tmp = sb.toString();

        return tmp;
    }

}
