package edu.chl.proton.model;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-14.
 */
public class MarkdownTest {

    private Markdown markdown = new Markdown();

    @Test public void getHTMLTest() {
        String str = "##The story of an hour\rIt was her sister *Josephine* who told her, " +
                "in broken sentences; veiled **hints** that revealed in half " +
                "concealing.\r Her husband's friend *Richards* was there, too, " +
                "near her.\r > It was he who had been in the newspaper office " +
                "when intelligence of the railroad disaster was received, " +
                "with Brently Mallard's name leading ***the list of killed:***\r" +
                "* Anthony Meier\r" +
                "*Ron Swanson\r" +
                "- Bethany Swanson\r" +
                "-Sarah Clarksson\r" +
                "He had only taken the time to assure himself of its truth by a " +
                "second [telegram](link to stuff), and had ![hastened](dunno) to forestall any less careful," +
                " less tender friend in bearing the sad message.\r";
        String outcome = "<h2>The story of an hour</h2>\rIt was her sister <i>Josephine</i> who told her, " +
                "in broken sentences; veiled <b>hints</b> that revealed in half " +
                "concealing.\r Her husband's friend <i>Richards</i> was there, too, " +
                "near her.\r > It was he who had been in the newspaper office " +
                "when intelligence of the railroad disaster was received, " +
                "with Brently Mallard's name leading <i><b>the list of killed:</b></i>\r" +
                "<ul><li> Anthony Meier</li>\r" +
                "<li>Ron Swanson</li>\r" +
                "<li> Bethany Swanson</li>\r" +
                "<li>Sarah Clarksson</li></ul>\r" +
                "He had only taken the time to assure himself of its truth by a " +
                "second <a href=\"link to stuff\">telegram</a>, and had <img src=\"dunno\" alt=\"hastened\"> to forestall any less careful," +
                " less tender friend in bearing the sad message.\r";
        //assertTrue(markdown.getHTML());

    }



}
