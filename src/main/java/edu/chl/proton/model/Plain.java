package edu.chl.proton.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ludvig on 2017-05-26.
 * Plain document type, which do not follow markdown standard or any other standard.
 */
public class Plain implements IDoc {

    private List<String> lines = new ArrayList<>();

    public List<String> getLines(){
        return lines;
    }

    public void setText(List<String> str){
        this.lines = str;
    }

    @Override
    public String getHTML(){
        String tmp = "";
        for(String str : lines){
            tmp = tmp + "<p style=\"width:100%\">" +str + "</p>";
        }
        return tmp;
    }

    @Override
    public String getText(){
        return getHTML();
    }
}
