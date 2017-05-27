package edu.chl.proton.model;

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

        String outcome = "<p style=\"width:100%\"><h2>The story of an hour</h2></p>" +
                "<p style=\"width:100%\">It was her sister <i>Josephine</i> who told her, in broken sentences; " +
                "veiled <b>hints</b> that revealed in half concealing.</p><p style=\"width:100%\">Her husband's " +
                "friend <i>Richards</i> was there, too, near her.</p><p style=\"width:100%\"><blockquote> It was " +
                "he who had been in the newspaper office when intelligence of the railroad disaster was received, " +
                "with Brently Mallard's name leading ***the list of killed:***</blockquote></p>" +
                "<p style=\"width:100%\">He had only taken the time to assure himself of its truth by a second " +
                "<a href=\"link to stuff\">telegram</a>, and had " +
                "<img style=\"max-width:100%\" src=\"dunno\" alt=\"hastened\"> to forestall any less careful, less " +
                "tender friend in bearing the sad message.</p>";

        assertTrue("Text should get HTML tags: ", markdown.getHTML().equals(outcome));
    }
}
