package edu.chl.proton.model;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * @author Stina Werme
 * Created by stinawerme on 11/05/17.
 */
public class FolderTest {

    @Test
    public void removeFolderTest() {

        Folder parent = new Folder("parent");
        Folder child = new Folder("child", parent);

        assertTrue("The folder parent should not be null", parent.getFolders().size() == 1);

        parent.removeFolder(child);

        assertTrue("The folder parent should not be null", parent.getFolders().size() == 0);
        assertTrue("ParentFolder should be null", child.getParentFolder() == null);
    }

    @Test
    public void removeFileTest() {

        File file = new File("file");
        Folder folder = new Folder("folder");

        folder.removeFile(file);

        assertTrue("The file file should not be null", file != null);
        assertTrue("The folder folder should not be null", folder != null);
        assertTrue("ParentFolder should be null", file.getParentFolder() == null);
    }

    @Test
    public void addFileTest() {

        Folder parent = new Folder("parent");
        File childFile = new File("childFile");

        assertTrue("The folder parent should not be null", parent.getFiles().size() == 0);

        parent.addFile(childFile);

        assertTrue("The folder parent should not be null", parent.getFiles().size() == 1);
        assertTrue("ParentFolder should be newParent", childFile.getParentFolder() == parent);
    }

    @Test
    public void addFolderTest() {

        Folder one = new Folder("one");
        Folder two = new Folder("two", one);
        Folder three = new Folder("three");

        two.addFolder(three);

        assertTrue("The folder one should not be null", one != null);
        assertTrue("The folder two should not be null", two != null);
        assertTrue("The folder three should not be null", three != null);
        assertTrue("ParentFolder should be three", two.getParentFolder() == three);
    }
}