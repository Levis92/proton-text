package edu.chl.proton.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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
}
