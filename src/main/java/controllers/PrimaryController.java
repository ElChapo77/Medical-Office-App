package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.nacu.App;

import java.io.IOException;

public class PrimaryController {

    @FXML
    private Button startButton;

    @FXML
    private void switchToSecondary(ActionEvent actionEvent) throws IOException {
        App.setRoot("secondary");
    }
}
