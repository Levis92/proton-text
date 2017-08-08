package edu.chl.proton.model.documents;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by ludvig on 2017-08-08.
 */
public interface IDocument {
    boolean doesExist();
    File getFile();
    String getPath();
    String getText();
    void setText(List<String> text);
    String getHTML();
    void save(String path) throws IOException;
    boolean save() throws IOException;
    void removeFile();
    String getDateForLastEdited();
    boolean isSaved();
    void aqcuireText();
}
