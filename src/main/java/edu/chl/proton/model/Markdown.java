package edu.chl.proton.model;

import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-01.
 */
public class Markdown extends Document{


    public Markdown(){
        
    }

    public Markdown(File file){
        getCursor().setPosition(0,0);
        this.setFile(file);
    }

    // getHTML() returns a List with the resulting HTML from the Markdown text
    protected void getHTML(){

    }

    // Returns a formatted List, where every list item is a row
    // in the document and every character gets a style.
    @Override
    protected List<Text> getText(){
        List<javafx.scene.text.Text> text = new ArrayList<javafx.scene.text.Text>();
        javafx.scene.text.Text newText = new Text("Bä, bä, vita lamm, har du någon ull?\n" +
                "Ja, ja, lilla barn, jag har säcken full.\n" +
                "Helgdagsrock åt far och söndagskjol åt mor\n" +
                "och två par strumpor åt lille, lille bror.");

        text.add(newText);

        return text;

    }

}
