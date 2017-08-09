package edu.chl.proton.model.documents;

import java.util.regex.*;

/**
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
 *
 *
 * Created by Mickaela on 2017-08-08.
 * Responsible for finding and formatting matches of the pattern group "Headings"
 */
public class CheckHeading implements ICheckPattern {

    /**
     * Compiles a pattern and returns it. Throws
     * PatternSyntaxException if it fails.
     * @param pattern
     * @return the specified pattern
     */
    @Override
    public Pattern pattern(String pattern) {
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

        if(pattern.equals("h6")){
            return h6;
        } else if(pattern.equals("h5")){
            return h5;
        } else if(pattern.equals("h4")){
            return h4;
        } else if(pattern.equals("h3")){
            return h3;
        } else if(pattern.equals("h2")){
            return h2;
        } else if(pattern.equals("h1")){
            return h1;
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
        // Check for h6
        Matcher match = pattern("h6").matcher(text);
        StringBuffer sb = new StringBuffer();
        if (match.find()) {
            match.appendReplacement(sb, "<h6 style=\"font-family: helvetica; color: #444; border-bottom: 1px solid lightgrey; padding-bottom: 0.2em\">$2</h6>");
            match.appendTail(sb);
            return sb.toString();
        }

        // Check for h5
        match = pattern("h5").matcher(text);
        sb = new StringBuffer();
        if(match.find()) {
            match.appendReplacement(sb, "<h5 style=\"font-family: helvetica; color: #444; border-bottom: 1px solid lightgrey; padding-bottom: 0.2em\">$2</h5>");
            match.appendTail(sb);
            return sb.toString();
        }

        // check for h4
        match = pattern("h4").matcher(text);
        sb = new StringBuffer();
        if (match.find()){
            match.appendReplacement(sb, "<h4 style=\"font-family: helvetica; color: #444; border-bottom: 1px solid lightgrey; padding-bottom: 0.2em\">$2</h4>");
            match.appendTail(sb);
            return sb.toString();
        }

        // check for h3
        match = pattern("h3").matcher(text);
        sb = new StringBuffer();
        if (match.find()){
            match.appendReplacement(sb, "<h3 style=\"font-family: helvetica; color: #444; border-bottom: 1px solid lightgrey; padding-bottom: 0.2em\">$2</h3>");
            match.appendTail(sb);
            return sb.toString();
        }

        // check for h2
        match = pattern("h2").matcher(text);
        sb = new StringBuffer();
        if (match.find()){
            match.appendReplacement(sb, "<h2 style=\"font-family: helvetica; color: #444; border-bottom: 1px solid lightgrey; padding-bottom: 0.2em\">$2</h2>");
            match.appendTail(sb);
            return sb.toString();
        }

        // check for h1
        match = pattern("h1").matcher(text);
        sb = new StringBuffer();
        if (match.find()){
            match.appendReplacement(sb, "<h1 style=\"font-family: helvetica; color: #444; border-bottom: 1px solid lightgrey; padding-bottom: 0.2em\">$2</h1>");
            match.appendTail(sb);
            return sb.toString();
        }
        // no matches found, return original string
        return text;
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

        //check for heading 6
        Matcher match = pattern("h6").matcher(tmp);
        while(match.find()) {
            tmp = match.replaceAll(style.getHeadingStyle(match.group(0)));
        }
        // Check for h5
        match = pattern("h5").matcher(tmp);
        while(match.find()) {
            tmp = match.replaceAll(style.getHeadingStyle(match.group(0)));
        }

        // check for h4
        match = pattern("h4").matcher(tmp);
        while(match.find()) {
            tmp = match.replaceAll(style.getHeadingStyle(match.group(0)));
        }

        // check for h3
        match = pattern("h3").matcher(tmp);
        while(match.find()) {
            tmp = match.replaceAll(style.getHeadingStyle(match.group(0)));
        }

        // check for h2
        match = pattern("h2").matcher(tmp);
        while(match.find()) {
            tmp = match.replaceAll(style.getHeadingStyle(match.group(0)));
        }

        // check for h1
        match = pattern("h1").matcher(tmp);
        while(match.find()) {
            tmp = match.replaceAll(style.getHeadingStyle(match.group(0)));
        }
        return tmp;
    }
}
