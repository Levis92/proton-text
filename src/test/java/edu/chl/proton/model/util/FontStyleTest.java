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

package edu.chl.proton.model.util;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import edu.chl.proton.model.util.FontStyle;

/**
 * Ludvig Ekman
 * Created by luddevig on 2017-08-12.
 */

public class FontStyleTest {
    private FontStyle fontStyle;

    @Test
    public void getItalicStyleTest() {
        FontStyle fontStyle = new FontStyle();
        String testString = "This is certainly a rather good test String.";
        testString = fontStyle.getItalicStyle(testString);
        assertTrue("String should return italic tags", testString.equals(
                "<i>This is certainly a rather good test String.</i>"));
    }

    @Test
    public void getItalicBoldStyleTest() {
        FontStyle fontStyle = new FontStyle();
        String testString = "This is certainly a rather good test String.";
        testString = fontStyle.getItalicBoldStyle(testString);
        assertTrue("String should return italic and bold tags", testString.equals(
                "<b><i>This is certainly a rather good test String.</i></b>"));
    }

    @Test
    public void getHeadingStyleTest() {
        FontStyle fontStyle = new FontStyle();
        String testString = "This is certainly a rather good test String.";
        testString = fontStyle.getHeadingStyle(testString);
        assertTrue("String should return heading (blue) tags", testString.equals(
                "<b><span style=\"color:#007E73\">This is certainly a rather good test String.</span></b>"));
    }

    @Test
    public void getLinkStyleTest() {
        FontStyle fontStyle = new FontStyle();
        String testString = "This is certainly a rather good test String.";
        testString = fontStyle.getLinkStyle(testString);
        assertTrue("String should return link tags", testString.equals(
                "<span style=\"text-decoration:underline;color:blue\">This is certainly a rather good test String.</span>"));
    }

    @Test
    public void getListStyleTest() {
        FontStyle fontStyle = new FontStyle();
        String testString = "This is certainly a rather good test String.";
        testString = fontStyle.getListStyle(testString);
        assertTrue("String should return list (blue) tags", testString.equals(
                "<span style=\"color:#007E73\">This is certainly a rather good test String.</span>"));
    }

    @Test
    public void getBoldStyleTest() {
        FontStyle fontStyle = new FontStyle();
        String testString = "This is certainly a rather good test String.";
        testString = fontStyle.getBoldStyle(testString);
        assertTrue("String should return bold tags", testString.equals(
                "<b>This is certainly a rather good test String.</b>"));
    }

    @Test
    public void getQuoteStyleTest() {
        FontStyle fontStyle = new FontStyle();
        String testString = "This is certainly a rather good test String.";
        testString = fontStyle.getQuoteStyle(testString);
        assertTrue("String should return quote (blue) tags", testString.equals(
                "<span style=\"color:#007E73\">This is certainly a rather good test String.</span>"));
    }
    @Test
    public void getCodeStyleTest() {
        FontStyle fontStyle = new FontStyle();
        String testString = "This is certainly a rather good test String.";
        testString = fontStyle.getCodeStyle(testString);
        assertTrue("String should return code (bold) tags", testString.equals(
                "<b>This is certainly a rather good test String.</b>"));
    }
}
