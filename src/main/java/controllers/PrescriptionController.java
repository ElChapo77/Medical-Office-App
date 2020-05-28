package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import model.appointment.Appointment;
import model.medicalprescription.MedicalPrescription;
import model.medicine.Aspirin;
import model.medicine.Medicine;
import model.medicine.Paracetamol;
import org.nacu.App;
import service.AppointmentService;
import service.AuditService;
import service.PrescriptionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionController {

    private static String patientUsername;
    private static Appointment appointment;

    @FXML
    private CheckBox aspirin;

    @FXML
    private CheckBox paracetamol;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button releaseButton;

    @FXML
    private void switchToMainView(ActionEvent actionEvent) throws IOException {
        List<Medicine> medicines = new ArrayList<>();
        if (aspirin.isSelected()) {
            medicines.add(new Aspirin());
        }
        if(paracetamol.isSelected()) {
            medicines.add(new Paracetamol());
        }

        String description = descriptionArea.getText();

        MedicalPrescription prescription = new MedicalPrescription(patientUsername, medicines, description);
        PrescriptionService prescriptionService = PrescriptionService.getInstance();
        prescriptionService.addPrescription(prescription);

        AppointmentService appointmentService = AppointmentService.getInstance();
        appointmentService.removeAppointment(appointment);

        AuditService.getInstance().writeAction(AuditService.ActionType.RELEASE_PRESCRIPTION);
        App.setRoot("mainView");
    }

    public static void setPatientUsername(String patientUsername) {
        PrescriptionController.patientUsername = patientUsername;
    }

    public static void setAppointment(Appointment appointment) {
        PrescriptionController.appointment = appointment;
    }
}
