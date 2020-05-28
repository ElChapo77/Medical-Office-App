package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.appointment.Appointment;
import model.medicaloffice.MedicalOffice;
import model.medicalprescription.MedicalPrescription;
import model.user.Doctor;
import model.user.Patient;
import model.user.User;
import org.nacu.App;
import service.AppointmentService;
import service.AuditService;
import service.MedicalOfficeService;
import service.PrescriptionService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    static private User user;

    @FXML
    private VBox layoutVBox;

    @FXML
    private Label title;

    public static void setUser(User user) {
        MainController.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setText(title.getText() + " " + user.getUsername());
        initializeAppointmentsLayout(user);
        if (user instanceof Doctor) {
            initializeDoctorOfficesLayout();
        } else {
            initializePrescriptionLayout();
            initializeAllOfficesLayout();
        }

        Button backButton = new Button("BACK");
        backButton.setOnAction(actionEvent -> switchToSecondary());
        layoutVBox.getChildren().add(backButton);
    }

    private void switchToSecondary() {
        try {
            App.setRoot("secondary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializePrescriptionLayout() {
        AuditService.getInstance().writeAction(AuditService.ActionType.DISPLAY_PRESCRIPTIONS);

        VBox prescriptionLayout = new VBox();
        prescriptionLayout.setStyle("-fx-spacing: 20pt");

        Label titleLabel = new Label("Your Prescriptions: ");
        titleLabel.setStyle("-fx-background-color: red");
        prescriptionLayout.getChildren().add(titleLabel);

        var prescriptionService = PrescriptionService.getInstance();
        var prescriptions = prescriptionService.findAllPrescriptionsForPatient((Patient) user);
        for (var prescription : prescriptions) {
            VBox vbox = new VBox();

            Label labelDescription = new Label("Description: " + prescription.getDescription());
            Label labelMedicines = new Label();
            String str = "Medicines: ";
            for (var medicine : prescription.getMedicines()) {
                str += medicine.getName() + " ";
            }
            labelMedicines.setText(str);

            Button removeButton = new Button("remove");
            removeButton.setOnAction(actionEvent -> removePrescription(prescription));

            vbox.getChildren().addAll(labelDescription, labelMedicines, removeButton);
            prescriptionLayout.getChildren().add(vbox);
        }

        layoutVBox.getChildren().add(prescriptionLayout);
    }

    private void removePrescription(MedicalPrescription prescription) {
        var prescriptionService = PrescriptionService.getInstance();
        prescriptionService.removePrescription(prescription);
        try {
            AuditService.getInstance().writeAction(AuditService.ActionType.FINISH_PRESCRIPTION);
            App.setRoot("mainView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeAppointmentsLayout(User user) {
        AuditService.getInstance().writeAction(AuditService.ActionType.DISPLAY_APPOINTMENTS);

        VBox appointmentsLayout = new VBox();
        appointmentsLayout.setStyle("-fx-spacing: 20pt");

        Label titleLabel = new Label("Your Appointments:");
        titleLabel.setStyle("-fx-background-color: red");
        appointmentsLayout.getChildren().add(titleLabel);

        var appointmentService = AppointmentService.getInstance();
        var appointments = appointmentService.getAppointments(user);
        for (var appointment : appointments) {
            Label label;
            HBox hbox = new HBox();
            hbox.setStyle("-fx-spacing: 10pt");

            if (user instanceof Patient) {
                label = new Label("You have an appointment on the date " + appointment.getDateAsString() + " at the " + appointment.getMedicalOffice().getName() + " of " + appointment.getMedicalOffice().getDoctorUsername() + ", which is located on " + appointment.getMedicalOffice().getAddress());
                hbox.getChildren().add(label);
            } else {
                label = new Label("You have an appointment with  " + appointment.getPatient().getUsername() + " on the date " + appointment.getDateAsString() + " at your " + appointment.getMedicalOffice().getName() + ", situated on " + appointment.getMedicalOffice().getAddress());
                Button prescriptionButton = new Button("Release Prescription");
                prescriptionButton.setOnAction(actionEvent -> switchToMedicalPrescription(appointment.getPatient().getUsername(), appointment));
                hbox.getChildren().addAll(label, prescriptionButton);
            }
            appointmentsLayout.getChildren().add(hbox);
        }

        layoutVBox.getChildren().add(appointmentsLayout);
    }

    private void switchToMedicalPrescription(String patientUsername, Appointment appointment) {
        try {
            PrescriptionController.setPatientUsername(patientUsername);
            PrescriptionController.setAppointment(appointment);
            AuditService.getInstance().writeAction(AuditService.ActionType.FINISH_APPOINTMENT);
            App.setRoot("prescription");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeAllOfficesLayout() {
        AuditService.getInstance().writeAction(AuditService.ActionType.DISPLAY_OFFICES);

        VBox allOfficesLayout = new VBox();
        allOfficesLayout.setStyle("-fx-spacing: 20pt");

        Label titleLabel = new Label("Medical Offices:");
        titleLabel.setStyle("-fx-background-color: red");
        allOfficesLayout.getChildren().add(titleLabel);

        MedicalOfficeService medicalOfficeService = MedicalOfficeService.getInstance();
        var medicalOffices = medicalOfficeService.getAllMedicalOffices();
        for (var medicalOffice : medicalOffices) {
            VBox nameVBox = new VBox();
            nameVBox.getChildren().addAll(new Label("Name:"), new Label(medicalOffice.getName()));

            VBox addressVBox = new VBox();
            addressVBox.getChildren().addAll(new Label("Address:"), new Label(medicalOffice.getAddress()));

            VBox doctorUsernameVBox = new VBox();
            doctorUsernameVBox.getChildren().addAll(new Label("Doctor Username:"), new Label(medicalOffice.getDoctorUsername()));

            Button appointmentButton = new Button("Make an appointment");
            appointmentButton.setOnAction(actionEvent -> switchToAppointment(medicalOffice));

            HBox hbox = new HBox();
            hbox.setStyle("-fx-spacing: 20pt");
            hbox.getChildren().addAll(nameVBox, addressVBox, doctorUsernameVBox, appointmentButton);

            allOfficesLayout.getChildren().add(hbox);
        }

        layoutVBox.getChildren().add(allOfficesLayout);
    }

    private void switchToAppointment(MedicalOffice medicalOffice) {
        try {
            AppointmentController.setPatient((Patient) user);
            AppointmentController.setMedicalOffice(medicalOffice);
            App.setRoot("appointment");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeDoctorOfficesLayout() {
        AuditService.getInstance().writeAction(AuditService.ActionType.DISPLAY_OFFICES);

        VBox doctorOfficesLayout = new VBox();
        doctorOfficesLayout.setStyle("-fx-spacing: 20pt");

        Label titleLabel = new Label("Your Medical Offices:");
        titleLabel.setStyle("-fx-background-color: red");
        doctorOfficesLayout.getChildren().add(titleLabel);

        MedicalOfficeService medicalOfficeService = MedicalOfficeService.getInstance();
        var medicalOffices = medicalOfficeService.findMedicalOffices(user.getUsername());
        for (var medicalOffice : medicalOffices) {
            VBox nameVBox = new VBox();
            nameVBox.getChildren().addAll(new Label("Name:"), new Label(medicalOffice.getName()));

            VBox addressVBox = new VBox();
            addressVBox.getChildren().addAll(new Label("Address:"), new Label(medicalOffice.getAddress()));

            HBox hbox = new HBox();
            hbox.setStyle("-fx-spacing: 20pt");
            hbox.getChildren().addAll(nameVBox, addressVBox);

            doctorOfficesLayout.getChildren().add(hbox);
        }

        Button addButton = new Button("Add A Medical Office");
        addButton.setOnAction(actionEvent -> switchToMedicalOffice());
        doctorOfficesLayout.getChildren().add(addButton);

        layoutVBox.getChildren().add(doctorOfficesLayout);
    }

    private void switchToMedicalOffice() {
        try {
            MedicalOfficeController.setDoctorUsername(user.getUsername());
            App.setRoot("medicalOffice");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
