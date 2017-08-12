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

import edu.chl.proton.model.documents.markdown.Markdown;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-14.
 */
public class MarkdownTest {

    private List<String> lines = new ArrayList<>();
    private Markdown markdown;

    @Test public void getHTMLTest() {
        String str = "##The story of an hour\rIt was her sister *Josephine* who told her, " +
                "in broken sentences; veiled **hints** that revealed in half " +
                "concealing.\rHer husband's friend *Richards* was there, too, " +
                "near her.\r> It was he who had been in the newspaper office " +
                "when intelligence of the railroad disaster was received, " +
                "with Brently Mallard's name leading ***the list of killed:***\r" +
                "He had only taken the time to assure himself of its truth by a " +
                "second [telegram](link to stuff), and had ![hastened](dunno) to forestall any less careful," +
                " less tender friend in bearing the sad message.\r";

        String[] original = str.split("\r");

        for(String s : original){
            this.lines.add(s);
        }

        markdown = new Markdown();
        markdown.setText(lines);

        String outcome = "<p style=\"width:100%; font-family: helvetica; color: #444\">" +
                "<h2 style=\"font-family: helvetica; color: #444; border-bottom: 1px solid lightgrey; " +
                "padding-bottom: 0.2em\">The story of an hour</h2></p><p style=\"width:100%; font-family: helvetica; " +
                "color: #444\">It was her sister <i>Josephine</i> who told her, in broken sentences; veiled <b>hints</b> " +
                "that revealed in half concealing.</p><p style=\"width:100%; font-family: helvetica; color: #444\">" +
                "Her husband's friend <i>Richards</i> was there, too, near her.</p><p style=\"width:100%; " +
                "font-family: helvetica; color: #444\"><blockquote style=\"border-left: 3px solid lightgrey; " +
                "padding-left: 15px; color: #555\"> It was he who had been in the newspaper office when intelligence " +
                "of the railroad disaster was received, with Brently Mallard's name leading ***the list of killed:***" +
                "</blockquote></p><p style=\"width:100%; font-family: helvetica; color: #444\">He had only taken the " +
                "time to assure himself of its truth by a second <a href=\"link to stuff\">telegram</a>, and had " +
                "<img style=\"max-width:100%\" src=\"dunno\" alt=\"hastened\"/> to forestall any less careful, less " +
                "tender friend in bearing the sad message.</p>";

        assertTrue("Text should get HTML tags: ", markdown.getHTML().equals(outcome));
    }
}
