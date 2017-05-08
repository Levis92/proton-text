package edu.chl.proton.model;

/**
 * Anton Levholm
 * Created by antonlevholm on 2017-05-03.
 */
public class WorkspaceFactory {
    private static Workspace workspace;

    public WorkspaceFactory() {
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
