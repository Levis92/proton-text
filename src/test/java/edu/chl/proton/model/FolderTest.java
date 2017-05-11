package edu.chl.proton.model;

import org.junit.Test;

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

        parent.removeFolder(child);

        assertTrue("The folder parent should not be null", parent != null);
        assertTrue("The folder child should not be null", child != null);
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
        File childFile = new File("childFile", parent);
        Folder newParent = new Folder("newParent");

        newParent.addFile(childFile);

        assertTrue("The folder parent should not be null", parent != null);
        assertTrue("The file childFile should not be null", childFile != null);
        assertTrue("The folder newParent should not be null", newParent != null);
        assertTrue("ParentFolder should be newParent", childFile.getParentFolder() == newParent);
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