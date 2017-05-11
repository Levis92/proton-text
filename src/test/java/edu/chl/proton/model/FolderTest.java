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
        System.out.print(child.getParentFolder());

        assertTrue("Instance of Folder should not be null", child != null);
    }

    @Test
    public void removeFileTest() {

        File file = new File("file");
        Folder folder = new Folder("folder");
    }
}