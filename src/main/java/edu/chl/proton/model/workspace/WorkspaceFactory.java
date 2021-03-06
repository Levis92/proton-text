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

import java.io.IOException;

/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-05-03.
 *
 * A factory for creating and returning a single instance of Workspace.
 */
public class WorkspaceFactory {
    private static Workspace workspace;

    public WorkspaceFactory() throws IOException {
        if (workspace == null) {
            synchronized (WorkspaceFactory.class) {
                if (workspace == null) {
                    workspace = new Workspace();
                }
            }
        }
    }

    /**
     * Returns the current Workspace.
     * @return the current Workspace instance.
     */
    public Workspace getWorkspace() {
        return workspace;
    }
}
