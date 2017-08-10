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

package edu.chl.proton.model.documents;

import edu.chl.proton.model.util.FontStyle;

import java.util.regex.*;

/**
 * Created by Mickaela on 2017-08-08.
 * Responsible for finding and formatting matches of the pattern group "Links".
 */
public class CheckLink implements ICheckPattern {
    /**
     * Compiles a pattern and returns it. Throws
     * PatternSyntaxException if it fails.
     * @param pattern
     * @return the specified pattern
     */
    @Override
    public Pattern pattern(String pattern) {
        Pattern textLink;
        Pattern picLink;
        try{
            textLink = Pattern.compile("(?<!\\!)\\[(?<text>[^\\]]*)\\]\\((?<link>[^\\)]*)\\)");
            picLink = Pattern.compile("\\!\\[(?<text>[^\\]]*)\\]\\((?<link>[^\\)]*)\\)");
        } catch (PatternSyntaxException ex){
            throw(ex);
        }

        if(pattern.equals("textLink")){
            return textLink;
        } else if(pattern.equals("picLink")){
            return picLink;
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
        // Check for img link
        Matcher match = pattern("picLink").matcher(text);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, "<img style=\"max-width:100%\" src=\"$2\" alt=\"$1\"/>");
        }
        match.appendTail(sb);
        String tmp = sb.toString();

        // check for text link
        match = pattern("textLink").matcher(tmp);
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
     * @param text the string to be formatted
     * @param style the style to be applid to the string
     * @return the formatted string
     */
    @Override
    public String checkPattern(String text, FontStyle style) {
        String tmp = text;
        // Check for img link
        Matcher match = pattern("picLink").matcher(tmp);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getLinkStyle(match.group(0)));
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for text link
        match = pattern("textLink").matcher(tmp);
        sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, style.getLinkStyle(match.group(0)));
        }
        match.appendTail(sb);
        tmp = sb.toString();

        return tmp;
    }
}
