package edu.chl.proton.model;

import javafx.scene.text.Text;

import java.util.List;

/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-02.
 */
public interface IFileHandler {

    void saveCurrentDocument();

    void setCurrentDirectory(Folder folder);

    Folder getCurrentDirectory();

    void setDirectory(String folderPath);

    String getDirectory();


}
