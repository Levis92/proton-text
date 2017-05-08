package edu.chl.proton.model;

import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-01.
 */
public class Markdown implements DocTypeInterface{

    private List<String> lines = new ArrayList<String>();
    private List<Parts> parts = new ArrayList<Parts>();

    public Markdown(List<String> lines, List<Parts> parts){
        this.lines = lines;
        this.parts = parts;
    }

    public Markdown(File file){
        getCursor().setPosition(0,0);
        this.setFile(file);

    }

    public Cursor getCursor(){
        return null;
    }

    public void setCursor(Cursor cursor){

    }

    public File getFile(){
        return null;
    }

    public void setFile(File file){

    }

    public void addFile(){

    }

    public void addParts(Parts parts){

    }

    public void removeParts(int index){

    }

    public void removeAllParts(){

    }

    public void addLines(String str){

    }

    public void removeLines(int index){

    }

    public void removeAllLines(){

    }

    public void save(){

    }

    // getHTML() returns a List with the resulting HTML from the Markdown text
    protected List<Parts> getHTML(){
        Parts part;
        for(String str : lines){
            part = checkForMarkdown(str);
            parts.add(part);
        }
        return parts;
    }

    protected Parts checkForMarkdown(String str){
        String tmp = checkPosture(str);
        tmp = checkHeading(tmp);
        tmp = checkLink(tmp);
        tmp = checkList(tmp);
        Parts part = new Parts(tmp);
        return part;
    }

    protected String checkPosture(String str){
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
            throw(ex);
        }

        // Check for italic and bold
        Matcher match = boldItalic.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, "<i><b>$1</b></i>");
        }
        match.appendTail(sb);
        String tmp = sb.toString();

        // check for bold
        match = bold.matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<b>$1</b>");
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for italic
        match = italic.matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<i>$1</i>");
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for quotes
        match = quote.matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<blockquote>$1</blockquote>");
        }
        match.appendTail(sb);
        tmp = sb.toString();
        return tmp;
    }

    protected String checkHeading(String str){
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

        // Check for h6
        Matcher match = h6.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, "<h6>$1</h6>");
        }
        match.appendTail(sb);
        String tmp = sb.toString();

        // Check for h5
        match = h5.matcher(str);
        sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, "<h5>$1</h5>");
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for h4
        match = h4.matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<h4>$1</h4>");
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for h3
        match = h3.matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<h3>$1</h3>");
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for h2
        match = h2.matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<h2>$1</h2>");
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for h1
        match = h1.matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<h1>$1</h1>");
        }
        match.appendTail(sb);
        tmp = sb.toString();
        return tmp;
    }

    protected String checkLink(String str){
        Pattern textLink;
        Pattern picLink;
        try{
            textLink = Pattern.compile("\\[(?<text>[^\\]]*)\\]\\((?<link>[^\\)]*)\\)");
            picLink = Pattern.compile("\\!\\[(?<text>[^\\]]*)\\]\\((?<link>[^\\)]*)\\)");
        } catch (PatternSyntaxException ex){
            System.out.println("checkLink" + ex); // remove in later version
            throw(ex);
        }

        // Check for h5
        Matcher match = picLink.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, "<img src=\"$2\" alt=\"$1\">");
        }
        match.appendTail(sb);
        String tmp = sb.toString();

        // check for h4
        match = textLink.matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<a href=\"$2\">$1</a>");
        }
        match.appendTail(sb);
        tmp = sb.toString();
        return tmp;
    }

    protected String checkList(String str){
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
        // Check for ordered list
        Matcher match = orderedList.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (match.find()) {
            match.appendReplacement(sb, "<ol>$1$2$3</ol>");
        }
        match.appendTail(sb);
        String tmp = sb.toString();

        // check for unordered list
        match = unorderedList.matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<ul>$1$2$3</ul>");
        }
        match.appendTail(sb);
        tmp = sb.toString();

        // check for unordered list
        match = list.matcher(tmp);
        sb = new StringBuffer();
        while(match.find()){
            match.appendReplacement(sb, "<li>$2</li>");
        }
        match.appendTail(sb);
        tmp = sb.toString();
        return tmp;
    }

    // Returns a formatted List, where every list item is a row
    // in the document and every character gets a style.
    public List<Text> getText(){
        List<Text> text = new ArrayList<Text>();
        Text newText = new Text("Bä, bä, vita lamm, har du någon ull?\n" +
                "Ja, ja, lilla barn, jag har säcken full.\n" +
                "Helgdagsrock åt far och söndagskjol åt mor\n" +
                "och två par strumpor åt lille, lille bror.");

        text.add(newText);

        return text;

    }

    public void setText(){

    }

}
