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

package edu.chl.proton.model;

import org.junit.Test;

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
        assertTrue("currentDocument should not be null", classUnderTest.getCurrentDocumentIndex() != 0);
    }

    @Test public void openDocumentTest() {

    }

    @Test public void removeDocumentTest() {

    }
}
