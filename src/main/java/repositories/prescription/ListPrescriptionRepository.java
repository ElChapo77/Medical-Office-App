package repositories.prescription;

import model.medicalprescription.MedicalPrescription;
import model.user.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListPrescriptionRepository implements PrescriptionRepository {

    private List<MedicalPrescription> medicalPrescriptions;

    public ListPrescriptionRepository() {
        this.medicalPrescriptions = new ArrayList<>();
    }

    @Override
    public void addPrescription(MedicalPrescription medicalPrescription) {
        medicalPrescriptions.add(medicalPrescription);
    }

    @Override
    public void removePrescription(MedicalPrescription medicalPrescription) {
        medicalPrescriptions.remove(medicalPrescription);
    }

    @Override
    public List<MedicalPrescription> findAllPrescriptionsForPatient(Patient patient) {
        return medicalPrescriptions.stream()
                .filter(x -> x.getPatientUsername().equals(patient.getUsername()))
                .collect(Collectors.toList());
    }
}
