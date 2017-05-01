package edu.chl.proton.model;

import java.util.*;

/**
 * @author
 * Created by Mickaela on 2017-05-01.
 */
public class Parts {

    private List<Parts> parts = new ArrayList<Parts>();
    private List<Char> chars = new ArrayList<Char>();

    public Parts(List<Parts> parts, List<Char> chars){
        this.parts = parts;
        this.chars = chars;
    }



}
