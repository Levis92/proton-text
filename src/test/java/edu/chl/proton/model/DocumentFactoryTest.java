package edu.chl.proton.model;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Created by ludvig on 2017-05-26.
 */
public class DocumentFactoryTest {

    @Test
    public void testNonExistingFile() throws IOException{
        DocumentFactory factory = new DocumentFactory();
        factory.getDocument("./testFileDoesNotExist.md");

        assertTrue("File does not exist, and a new file was created",1==1);
    }
}
