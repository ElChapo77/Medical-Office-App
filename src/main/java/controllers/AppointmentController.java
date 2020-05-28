package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.appointment.Appointment;
import model.medicaloffice.MedicalOffice;
import model.user.Patient;
import org.nacu.App;
import service.AppointmentService;
import service.AuditService;

import java.io.IOException;

public class AppointmentController {

    private static MedicalOffice medicalOffice;
    private static Patient patient;

    @FXML
    private TextField hourField;

    @FXML
    private TextField dayField;

    @FXML
    private TextField yearField;

    @FXML
    private TextField monthField;

    @FXML
    private Button submitButton;

    public void switchToMainView(ActionEvent actionEvent) throws IOException {
        int year = Integer.parseInt(yearField.getText());
        int month = Integer.parseInt(monthField.getText());
        int day = Integer.parseInt(dayField.getText());
        int hour = Integer.parseInt(hourField.getText());

        Appointment appointment = new Appointment(patient, medicalOffice, year, month, day, hour);
        AppointmentService appointmentService = AppointmentService.getInstance();
        appointmentService.addAppointment(appointment);

        AuditService.getInstance().writeAction(AuditService.ActionType.MAKE_APPOINTMENT);
        App.setRoot("mainView");
    }

    public static void setMedicalOffice(MedicalOffice medicalOffice) {
        AppointmentController.medicalOffice = medicalOffice;
    }

    public static void setPatient(Patient patient) {
        AppointmentController.patient = patient;
    }
}
