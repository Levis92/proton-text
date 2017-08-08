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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-01.
 * A type of document where markdown syntax is used.
 * The text is checked for any type of markdown syntax . Whenever a match is found,
 * the match is formatted with appropriate HTML styling for both the preview view and the editor view.
 */
public class Markdown implements IDoc {

    private List<String> lines = new ArrayList<>();

    private ICheckPattern checkPosture;
    private ICheckPattern checkHeading;
    private ICheckPattern checkLink;
    private ICheckPattern checkList;
    private ICheckPattern checkCode;
    private ICheckPattern checkExtra;

    public Markdown(){
        checkPosture = new CheckPosture();
        checkHeading = new CheckHeading();
        checkLink = new CheckLink();
        checkList = new CheckList();
        checkCode = new CheckCode();
        checkExtra = new CheckExtra();
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
        String tmp = checkPosture.checkPattern(str);
        tmp = checkHeading.checkPattern(tmp);
        tmp = checkLink.checkPattern(tmp);
        tmp = checkList.checkPattern(tmp);
        tmp = checkCode.checkPattern(tmp);
        tmp = checkExtra.checkPattern(tmp);
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

            tmp = checkPosture.checkPattern(str, style);
            tmp = checkHeading.checkPattern(tmp, style);
            tmp = checkLink.checkPattern(tmp, style);
            tmp = checkList.checkPattern(tmp, style);
            tmp = checkCode.checkPattern(tmp, style);
            tmp = checkExtra.checkPattern(tmp, style);

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