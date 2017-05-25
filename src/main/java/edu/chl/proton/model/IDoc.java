package edu.chl.proton.model;

import java.util.List;

/**
 * @author Mickaela
 * Created by Mickaela on 2017-05-04.
 */
public interface IDoc {

    String getText();
    void setText(List<String> str);
    String getHTML();
    List<String> getLines();

}
