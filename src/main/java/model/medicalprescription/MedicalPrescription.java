package model.medicalprescription;

import model.medicine.Medicine;

import java.util.ArrayList;
import java.util.List;

public class MedicalPrescription {

    private String patientUsername;
    private List<Medicine> medicines;
    private String description;

    public MedicalPrescription(String patientUsername, List<Medicine> medicines, String description) {
        this.patientUsername = patientUsername;
        this.medicines = medicines;
        this.description = description;
    }

    public String getPatientUsername() {
        return patientUsername;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public String getDescription() {
        return description;
    }

    public String getMedicinesAsString() {
        return medicines.stream().map(Medicine::getName).reduce("", (x, y) -> x + "," + y).replaceFirst(",", "");
    }
}
