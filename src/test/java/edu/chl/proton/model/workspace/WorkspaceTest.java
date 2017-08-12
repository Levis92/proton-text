/*
 * Proton Text - A Markdown text editor
 * Copyright (C) 2017  Anton Levholm, Ludvig Ekman, Mickaela Södergren
 * and Stina Werme
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.chl.proton.model.workspace;

import edu.chl.proton.model.documents.DocumentType;
import edu.chl.proton.model.workspace.Workspace;
import edu.chl.proton.model.workspace.WorkspaceFactory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-02.
 */
public class WorkspaceTest {
    private Workspace workspace;

    public WorkspaceTest() throws IOException {
        workspace = new WorkspaceFactory().getWorkspace();
    }

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

    @Test public void openDocumentTest() throws IOException {
        Workspace classUnderTest = workspace;
        classUnderTest.createDocument(DocumentType.MARKDOWN);
        String path = "./Proton Text Directory/testFile";
        try {
            classUnderTest.saveCurrentDocument(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        classUnderTest.openDocument(classUnderTest.getPath());
        assertTrue("currentDocument should be open in a tab.", classUnderTest.getCurrentDocumentIndex() != 0);
    }

    @Test public void removeCurrentDocumentTest() {
        Workspace classUnderTest = workspace;
        classUnderTest.createDocument(DocumentType.MARKDOWN);
        String path = "./Proton Text Directory/testFile";
        try {
            classUnderTest.saveCurrentDocument(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(path);
        classUnderTest.removeCurrentDocument();
        assertTrue("isAlreadyOpen() should return -1", classUnderTest.isAlreadyOpen(file) == -1);

    }

    @Test public void isAlreadyOpenTest() {
        Workspace classUnderTest = workspace;
        classUnderTest.createDocument(DocumentType.MARKDOWN);
        String path = "./Proton Text Directory/testFile";
        try {
            classUnderTest.saveCurrentDocument(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(path);
        assertTrue("isAlreadyOpen() should not return -1", classUnderTest.isAlreadyOpen(file) != -1);
    }
}
