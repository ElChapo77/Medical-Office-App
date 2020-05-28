package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.user.Doctor;
import model.user.User;
import org.nacu.App;
import service.AuditService;
import service.LoginService;

import java.io.IOException;

public class LoginDoctorController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private void switchToMain(ActionEvent actionEvent) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = new Doctor(username, password);
        LoginService loginService = LoginService.getInstance();
        if(loginService.login(user)) {
            MainController.setUser(user);
            AuditService.getInstance().writeAction(AuditService.ActionType.LOGIN);
            App.setRoot("mainView");
        }
        else {
            loginButton.setText("TRY AGAIN!");
        }
    }
}
