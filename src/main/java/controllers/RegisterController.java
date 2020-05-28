package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.user.Doctor;
import model.user.Patient;
import model.user.User;
import org.nacu.App;
import service.AuditService;
import service.LoginService;

import java.io.IOException;

public class RegisterController {

    @FXML
    private RadioButton typeDoctor;

    @FXML
    private RadioButton typePatient;

    @FXML
    private RadioButton genderMale;

    @FXML
    private RadioButton genderFemale;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField ageField;

    @FXML
    private ToggleGroup type;

    @FXML
    private ToggleGroup gender;

    @FXML
    private Button submitButton;

    @FXML
    private void registerUser(ActionEvent actionEvent) throws IOException {
        int id = 1;
        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        boolean isMale = genderMale.isSelected();

        User user;

        if (typeDoctor.isSelected()) {
            user = new Doctor(id, username, password, name, age, isMale);
        } else {
            user = new Patient(id, username, password, name, age, isMale);
        }

        LoginService loginService = LoginService.getInstance();
        loginService.register(user);
        AuditService.getInstance().writeAction(AuditService.ActionType.REGISTER);

        App.setRoot("secondary");
    }
}
