package edu.chl.proton.model;

import java.util.*;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-01.
 */
public class Markdown extends Document{


    public Markdown(Cursor cursor, File file, List<Parts> parts, List<String> lines){
        this.setCursor(cursor);
        this.setFile(file);
        this.setText(lines);
        for(Parts part : parts){
            addParts(part);
        }
    }

}
