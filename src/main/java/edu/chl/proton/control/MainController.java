package edu.chl.proton.control;

import com.jfoenix.controls.JFXTabPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by antonlevholm on 2017-05-01.
 */
public class MainController implements Observer {
    @Override
    public void update(Observable o, Object arg) {

    }

    @FXML
    public void test(ActionEvent event) throws IOException {
        System.out.println("hej");
    }

    @FXML
    private JFXTabPane tabPane;

    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/chl/proton/view/markdown-tab.fxml"));
        Tab tab = new Tab("Untitled");
        tab.setContent(loader.load());
        tabPane.getTabs().add(tab);
    }
}
