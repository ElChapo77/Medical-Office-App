package repositories.prescription;

import managers.DBConnectionManager;
import model.medicalprescription.MedicalPrescription;
import model.medicine.Aspirin;
import model.medicine.Medicine;
import model.medicine.Paracetamol;
import model.user.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DBPrescriptionRepository implements PrescriptionRepository {

    @Override
    public void addPrescription(MedicalPrescription medicalPrescription) {
        String sql = "INSERT INTO prescriptions VALUES (NULL, ?, ?, ?)";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setString(1, medicalPrescription.getPatientUsername());
            statement.setString(2, medicalPrescription.getMedicinesAsString());
            statement.setString(3, medicalPrescription.getDescription());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removePrescription(MedicalPrescription medicalPrescription) {
        String sql = "DELETE FROM prescriptions WHERE patientUsername = ? and medicines = ? and description = ?";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setString(1, medicalPrescription.getPatientUsername());
            statement.setString(2, medicalPrescription.getMedicinesAsString());
            statement.setString(3, medicalPrescription.getDescription());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MedicalPrescription> findAllPrescriptionsForPatient(Patient patient) {
        String sql = "SELECT * FROM prescriptions WHERE patientUsername = ?";
        List<MedicalPrescription> medicalPrescriptions = new ArrayList<>();

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setString(1, patient.getUsername());

            ResultSet set = statement.executeQuery();
            while (set.next()) {
                String patientUsername = set.getString("patientUsername");
                String medicinesAsString = set.getString("medicines");
                String description = set.getString("description");

                List<Medicine> medicines =
                        Arrays.stream(medicinesAsString.split(","))
                                .map(x -> {
                                    switch (x) {
                                        case "Aspirin": return new Aspirin();
                                        default: return new Paracetamol();
                                    }
                                })
                                .collect(Collectors.toList());

                medicalPrescriptions.add(new MedicalPrescription(patientUsername, medicines, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicalPrescriptions;
    }
}
