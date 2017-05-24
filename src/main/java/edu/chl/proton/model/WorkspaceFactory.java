package edu.chl.proton.model;

import java.io.IOException;

/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-03.
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

    public Workspace getWorkspace() {
        return workspace;
    }
}
