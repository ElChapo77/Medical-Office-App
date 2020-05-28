package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.nacu.App;

import java.io.IOException;

public class SecondaryController {

    @FXML
    private Button patientButton;

    @FXML
    private Button doctorButton;

    @FXML
    private Button registerButton;

    @FXML
    public void switchToRegister(ActionEvent actionEvent) throws IOException {
        App.setRoot("register");
    }

    @FXML
    private void switchToLoginDoctor(ActionEvent actionEvent) throws IOException {
        App.setRoot("loginDoctor");
    }

    @FXML
    private void switchToLoginPatient(ActionEvent actionEvent) throws IOException {
        App.setRoot("loginPatient");
    }
}
