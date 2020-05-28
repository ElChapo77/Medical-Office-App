package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.medicaloffice.*;
import org.nacu.App;
import service.AuditService;
import service.MedicalOfficeService;

import java.io.IOException;

public class MedicalOfficeController {

    private static String doctorUsername;

    @FXML
    private RadioButton typeDental;

    @FXML
    private RadioButton typeDermatological;

    @FXML
    private RadioButton typeOphthalmic;

    @FXML
    private RadioButton typeOrthopedic;

    @FXML
    private ToggleGroup officeType;

    @FXML
    private Button submitButton;

    @FXML
    private TextField addressField;

    public static void setDoctorUsername(String doctorUsername) {
        MedicalOfficeController.doctorUsername = doctorUsername;
    }

    @FXML
    private void addMedicalOffice(ActionEvent actionEvent) throws IOException {
        String address = addressField.getText();
        MedicalOffice medicalOffice;

        if (typeDental.isSelected()) {
            medicalOffice = new DentalOffice(doctorUsername, address);
        } else if (typeDermatological.isSelected()) {
            medicalOffice = new DermatologicalOffice(doctorUsername, address);
        } else if (typeOphthalmic.isSelected()) {
            medicalOffice = new OphthalmicOffice(doctorUsername, address);
        } else {
            medicalOffice = new OrthopedicOffice(doctorUsername, address);
        }

        MedicalOfficeService medicalOfficeService = MedicalOfficeService.getInstance();
        medicalOfficeService.addMedicalOffice(medicalOffice);
        AuditService.getInstance().writeAction(AuditService.ActionType.CREATE_OFFICE);

        App.setRoot("mainView");
    }
}
