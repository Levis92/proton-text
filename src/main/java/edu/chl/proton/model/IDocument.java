package edu.chl.proton.model;

import java.util.List;

/**
 * Created by ludvig on 2017-08-08.
 */
public interface IDocument {
    List<String> getLines();
    String getText();
    String getHTML();

}
