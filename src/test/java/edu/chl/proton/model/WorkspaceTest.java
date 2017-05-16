package edu.chl.proton.model;


import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-02.
 */
public class WorkspaceTest {
    private Workspace workspace = (new WorkspaceFactory()).getWorkspace();

    @Test
    public void InstanceTest() {
        Workspace classUnderTest = workspace;
        assertTrue("Instance of Workspace should not be null", classUnderTest != null);
    }

    @Test public void createDocumentTest() {
        Workspace classUnderTest = workspace;
        classUnderTest.createDocument(DocumentType.MARKDOWN);
        assertTrue("currentDocument should not be null", classUnderTest.getCurrentDocument() != null);
    }

    @Test public void openDocumentTest() {

    }

    @Test public void removeDocumentTest() {

    }
}
