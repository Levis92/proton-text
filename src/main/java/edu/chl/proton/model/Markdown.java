package edu.chl.proton.model;

import java.util.*;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-01.
 */
public class Markdown extends Document{


    public Markdown(File file){
        getCursor().setPosition(0,0);
        this.setFile(file);
    }



}
