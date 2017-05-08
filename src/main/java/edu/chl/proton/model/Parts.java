package edu.chl.proton.model;

import java.util.*;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-01.
 */
public class Parts {

    private List<Parts> parts = new ArrayList<Parts>();
    private List<Char> chars = new ArrayList<Char>();
    private String str;

    public Parts(List<Parts> parts, List<Char> chars){
        this.parts = parts;
        this.chars = chars;
    }

    public Parts(String str){
        this.str = str;
    }



}
