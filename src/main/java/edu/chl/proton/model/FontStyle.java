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

/**
 * @author Ludvig Ekman
 * Created by ludvig on 2017-05-01.
 * Each method returns the given text with a html code around it,
 * marking that the text has a style of some sort.
 * Used in html-view.
 */
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
