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
    }
}