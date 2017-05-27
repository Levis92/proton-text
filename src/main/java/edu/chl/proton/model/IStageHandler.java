package edu.chl.proton.model;

import javafx.stage.Stage;

/**
 * @author Anton Levholm
 * Created by antonlevholm on 2017-05-22.
 */
public interface IStageHandler {

    /**
     * Returns the Stage of the current instance of the application.
     * @return
     */

    Stage getStage();
}
