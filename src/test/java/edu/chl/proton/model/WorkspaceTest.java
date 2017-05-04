package edu.chl.proton.model;

import org.testng.annotations.Test;
import static org.junit.Assert.assertTrue;

/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-02.
 */
public class WorkspaceTest {

    @Test public void InstanceTest() {
        Workspace classUnderTest = Workspace.getInstance();
        assertTrue("Instance of Workspace should not be null", classUnderTest != null);
    }

    @Test public void createDocumentTest() {
        Workspace classUnderTest = Workspace.getInstance();
        classUnderTest.createDocument(DocumentType.MARKDOWN);
        assertTrue("currentDocument should not be null", classUnderTest.getCurrentDocument() != null);
        assertTrue("currentDocument should not be null", classUnderTest.getCurrentDocument() instanceof Markdown);
    }

    @Test public void openDocumentTest() {

    }

    @Test public void removeDocumentTest() {

    }
}
