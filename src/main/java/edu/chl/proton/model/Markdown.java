package edu.chl.proton.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-01.
 */
public class Markdown implements IDoc {

    private List<String> lines = new ArrayList<>();

    public Markdown(List<String> lines){
        this.lines = lines;
    }

    public Markdown(){
        //TODO?
    }

    // getHTML() returns a List with the resulting HTML from the Markdown text
    protected List<String> getHTML(){
        String tmp;
        List<String> formattedLines = new ArrayList<>();
        for(String str : lines){
            tmp = checkForMarkdown(str);
            formattedLines.add(tmp);
        }
        return formattedLines;
    }

    protected String checkForMarkdown(String str){
        String tmp = checkPosture(str);
        tmp = checkHeading(tmp);
        tmp = checkLink(tmp);
        tmp = checkList(tmp);
        tmp = checkCode(tmp);
        return tmp;
    }

    protected Pattern posturePattern(String str) {
        Pattern bold;
        Pattern italic;
        Pattern boldItalic;
        Pattern quote;

        try {
            boldItalic = Pattern.compile("\\*{3}([\\w\\d\\W\\D]+)\\*{3}");
            bold = Pattern.compile("\\*{2}([\\w\\d\\W\\D]+)\\*{2}");
            italic = Pattern.compile("\\*([\\w\\d]+)\\*");
            quote = Pattern.compile("\\>(?<text>[^\\>]*)\\\r");
        } catch (PatternSyntaxException ex) {
            System.out.println("checkPosture" + ex); // remove in later version
            throw (ex);
        }
        if(str.equals("bold")){
            return bold;
        } else if(str.equals("italic")){
            return italic;
        } else if(str.equals("boldItalic")){
            return boldItalic;
        } else if(str.equals("quote")){
            return quote;
        }
        return null;
    }

    protected String checkPosture(String str){

        // Check for italic and bold
        Matcher match = posturePattern("boldItalic").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, "<i><b>$1</b></i>");
        }
        match.appendTail(sb);
        String tmp = sb.toString();

        // check for bold
        match = posturePattern("bold").matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<b>$1</b>");
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for italic
        match = posturePattern("italic").matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<i>$1</i>");
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for quotes
        match = posturePattern("quote").matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<blockquote>$1</blockquote>");
        }
        match.appendTail(sb);
        tmp = sb.toString();
        return tmp;
    }

    protected Pattern headingPattern(String str){
        Pattern h1;
        Pattern h2;
        Pattern h3;
        Pattern h4;
        Pattern h5;
        Pattern h6;

        try{
            h1 = Pattern.compile("\\#(?<text>[^\\#]*)\\\r");
            h2 = Pattern.compile("\\#{2}(?<text>[^\\#]*)\\\r");
            h3 = Pattern.compile("\\#{3}(?<text>[^\\#]*)\\\r");
            h4 = Pattern.compile("\\#{4}(?<text>[^\\#]*)\\\r");
            h5 = Pattern.compile("\\#{5}(?<text>[^\\#]*)\\\r");
            h6 = Pattern.compile("\\#{6}(?<text>[^\\#]*)\\\r");
        } catch (PatternSyntaxException ex){
            System.out.println("checkHeading: " + ex); // remove in later version
            throw(ex);
        }

        if(str.equals("h6")){
            return h6;
        } else if(str.equals("h5")){
            return h5;
        } else if(str.equals("h4")){
            return h4;
        } else if(str.equals("h3")){
            return h3;
        } else if(str.equals("h2")){
            return h2;
        } else if(str.equals("h1")){
            return h1;
        }
        return null;
    }

    protected String checkHeading(String str){
        // Check for h6
        Matcher match = headingPattern("h6").matcher(str);
        StringBuffer sb = new StringBuffer();
        if (match.find()) {
            match.appendReplacement(sb, "<h6>$1</h6>");
            match.appendTail(sb);
            return sb.toString();
        }

        // Check for h5
        match = headingPattern("h5").matcher(str);
        sb = new StringBuffer();
        if(match.find()) {
            match.appendReplacement(sb, "<h5>$1</h5>");
            match.appendTail(sb);
            return sb.toString();
        }

        // check for h4
        match = headingPattern("h4").matcher(str);
        sb = new StringBuffer();
        if (match.find()){
            match.appendReplacement(sb, "<h4>$1</h4>");
            match.appendTail(sb);
            return sb.toString();
        }

        // check for h3
        match = headingPattern("h3").matcher(str);
        sb = new StringBuffer();
        if (match.find()){
            match.appendReplacement(sb, "<h3>$1</h3>");
            match.appendTail(sb);
            return sb.toString();
        }

        // check for h2
        match = headingPattern("h2").matcher(str);
        sb = new StringBuffer();
        if (match.find()){
            match.appendReplacement(sb, "<h2>$1</h2>");
            match.appendTail(sb);
            return sb.toString();
        }

        // check for h1
        match = headingPattern("h1").matcher(str);
        sb = new StringBuffer();
        if (match.find()){
            match.appendReplacement(sb, "<h1>$1</h1>");
            match.appendTail(sb);
            return sb.toString();
        }
        // no matches found, return original string
        return str;
    }

    protected Pattern linkPattern(String str){
        Pattern textLink;
        Pattern picLink;
        try{
            textLink = Pattern.compile("\\[(?<text>[^\\]]*)\\]\\((?<link>[^\\)]*)\\)");
            picLink = Pattern.compile("\\!\\[(?<text>[^\\]]*)\\]\\((?<link>[^\\)]*)\\)");
        } catch (PatternSyntaxException ex){
            System.out.println("checkLink" + ex); // remove in later version
            throw(ex);
        }

        if(str.equals("textLink")){
            return textLink;
        } else if(str.equals("picLink")){
            return picLink;
        }
        return null;
    }

    protected String checkLink(String str){
        // Check for img link
        Matcher match = linkPattern("picLink").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, "<img src=\"$2\" alt=\"$1\">");
        }
        match.appendTail(sb);
        String tmp = sb.toString();

        // check for text link
        match = linkPattern("textLink").matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<a href=\"$2\">$1</a>");
        }
        match.appendTail(sb);
        tmp = sb.toString();
        return tmp;
    }

    protected Pattern listPattern(String str){
        Pattern unorderedList;
        Pattern orderedList;
        Pattern list;
        try{
            orderedList = Pattern.compile("(\\d\\.\\u0020|\\d\\.|\\d{2}\\.\\u0020|\\d{2}\\.)([\\w\\d\\W\\D]+)(\\\r)");
            unorderedList = Pattern.compile("(\\*\\u0020|\\*|\\-\\u0020|\\-)([\\w\\d\\W\\D]+)(\\\r)");
            list = Pattern.compile("(\\d\\.\\u0020|\\d\\.|\\d{2}\\.\\u0020|\\d{2}\\.|\\*\\u0020|\\*|\\-\\u0020|\\-)((?:(?!\\d\\.|\\d{2}\\.|\\*|\\-).)*?)\\\r");
        } catch (PatternSyntaxException ex){
            System.out.println("checkList" + ex); // remove in later version
            throw(ex);
        }
        if(str.equals("orderedList")){
            return orderedList;
        } else if(str.equals("unorderedList")){
            return unorderedList;
        } else if(str.equals("list")){
            return list;
        }
        return null;
    }

    protected String checkList(String str){
        // Check for ordered list
        Matcher match = listPattern("orderedList").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, "<ol>$1$2$3</ol>");
        }
        match.appendTail(sb);
        String tmp = sb.toString();

        // check for unordered list
        match = listPattern("unorderedList").matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<ul>$1$2$3</ul>");
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for unordered list
        match = listPattern("list").matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<li>$2</li>");
        }
        match.appendTail(sb);
        tmp = sb.toString();
        return tmp;
    }

    protected Pattern codePattern(String str){
        Pattern code;
        try{
        code = Pattern.compile("\\`{3}(?<text>[^\\`]*)\\`{3}");
        } catch(PatternSyntaxException ex){
            System.out.println("checkCode" + ex);
            throw(ex);
        }
        if(str.equals("code")){
            return code;
        }
        return null;
    }

    protected String checkCode(String str){
        Matcher match = codePattern("code").matcher(str);
        StringBuffer sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<div style=\"background-color: #f8f8f8; border: 1px solid #ddd; " +
                    "font-size: 13px; line-height: 19px; overflow: auto; padding: 6px 10px; border-radius: 3px; " +
                    "margin: 15px\"><code style=\"font-family: monospace\">$1</code></div>");
        }
        match.appendTail(sb);
        return sb.toString();
    }

    // Finds the markdown syntax, asks FontStyle for the correct FontStyle and
    // returns the formatted list.
    public List<String> getText(){
        FontStyle style = new FontStyle();
        List<String> text = new ArrayList<>();

        for(String str : lines) {

            // Check for italic and bold
            Matcher match = posturePattern("boldItalic").matcher(str);
            StringBuffer sb = new StringBuffer();
            while (match.find()) {
                match.appendReplacement(sb, style.getItalicBoldStyle(match.group(0)));
            }
            match.appendTail(sb);
            String tmp = sb.toString();

            // check for bold
            match = posturePattern("bold").matcher(tmp);
            sb = new StringBuffer();
            while(match.find()){
                match.appendReplacement(sb, style.getBoldStyle(match.group(0)));
            }
            match.appendTail(sb);
            tmp = sb.toString();

            // check for italic
            match = posturePattern("italic").matcher(tmp);
            sb = new StringBuffer();
            while(match.find()){
                match.appendReplacement(sb, style.getItalicStyle(match.group(0)));
            }
            match.appendTail(sb);
            tmp = sb.toString();

            // check for quotes
            match = posturePattern("quote").matcher(tmp);
            sb = new StringBuffer();
            while(match.find()){
                match.appendReplacement(sb, style.getQuoteStyle(match.group(0)));
            }
            match.appendTail(sb);
            tmp = sb.toString();

            //check for heading 6
            match = headingPattern("h6").matcher(tmp);
            sb = new StringBuffer();
            while(match.find()) {
                match.appendReplacement(sb, style.getHeadingStyle(match.group(0)));
            }
            match.appendTail(sb);
            tmp = sb.toString();

            // Check for h5
            match = headingPattern("h5").matcher(tmp);
            sb = new StringBuffer();
            while(match.find()) {
                match.appendReplacement(sb, style.getHeadingStyle(match.group(0)));
            }
            match.appendTail(sb);
            tmp = sb.toString();

            // check for h4
            match = headingPattern("h4").matcher(tmp);
            sb = new StringBuffer();
            while(match.find()){
                match.appendReplacement(sb, style.getHeadingStyle(match.group(0)));
            }
            match.appendTail(sb);
            tmp = sb.toString();

            // check for h3
            match = headingPattern("h3").matcher(tmp);
            sb = new StringBuffer();
            while(match.find()){
                match.appendReplacement(sb, style.getHeadingStyle(match.group(0)));
            }
            match.appendTail(sb);
            tmp = sb.toString();

            // check for h2
            match = headingPattern("h2").matcher(tmp);
            sb = new StringBuffer();
            while(match.find()){
                match.appendReplacement(sb, style.getHeadingStyle(match.group(0)));
            }
            match.appendTail(sb);
            tmp = sb.toString();

            // check for h1
            match = headingPattern("h1").matcher(tmp);
            sb = new StringBuffer();
            while(match.find()){
                match.appendReplacement(sb, style.getHeadingStyle(match.group(0)));
            }
            match.appendTail(sb);
            tmp = sb.toString();

            // Check for img link
            match = linkPattern("picLink").matcher(tmp);
            sb = new StringBuffer();
            while (match.find()) {
                match.appendReplacement(sb, style.getLinkStyle(match.group(0)));
            }
            match.appendTail(sb);
            tmp = sb.toString();

            // check for text link
            match = linkPattern("textLink").matcher(tmp);
            sb = new StringBuffer();
            while(match.find()){
                match.appendReplacement(sb, style.getLinkStyle(match.group(0)));
            }
            match.appendTail(sb);
            tmp = sb.toString();

            // Check for ordered list
            match = listPattern("orderedList").matcher(tmp);
            sb = new StringBuffer();
            while (match.find()) {
                match.appendReplacement(sb, style.getListStyle(match.group(0)));
            }
            match.appendTail(sb);
            tmp = sb.toString();

            // check for unordered list
            match = listPattern("unorderedList").matcher(tmp);
            sb = new StringBuffer();
            while(match.find()){
                match.appendReplacement(sb, style.getListStyle(match.group(0)));
            }
            match.appendTail(sb);
            tmp = sb.toString();

            // check for unordered list
            match = listPattern("list").matcher(tmp);
            sb = new StringBuffer();
            while(match.find()){
                match.appendReplacement(sb, style.getListStyle(match.group(0)));
            }
            match.appendTail(sb);
            tmp = sb.toString();

            // check for code
            match = codePattern("code").matcher(tmp);
            sb = new StringBuffer();
            while(match.find()){
                match.appendReplacement(sb, style.getCodeStyle(match.group(0)));
            }
            match.appendTail(sb);
            tmp = sb.toString();

            text.add(tmp);
        }
        return text;
    }

    public String setText(List<String> str){
        String tmp = "";
        for(String s : str){
            tmp = tmp + "<p>" + s + "</p>";
        }
        tmp = "<html dir=\"ltr\"><head></head><body contenteditable=\"true\"><p><font face=\"Segoe UI\">" + tmp + "</font></p></body></html>";
        return tmp;
    }
}