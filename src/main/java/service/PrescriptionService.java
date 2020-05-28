package service;

import model.medicalprescription.MedicalPrescription;
import model.user.Patient;
import repositories.prescription.PrescriptionRepository;

import java.util.List;

public class PrescriptionService {

    private PrescriptionRepository prescriptionRepository;

    private PrescriptionService() {
        prescriptionRepository = PrescriptionRepository.build(PrescriptionRepository.Type.DB);
    }

    public static PrescriptionService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static final class SingletonHolder {
        private static final PrescriptionService INSTANCE = new PrescriptionService();
    }

    public void addPrescription(MedicalPrescription prescription) {
        prescriptionRepository.addPrescription(prescription);
    }

    public List<MedicalPrescription> findAllPrescriptionsForPatient(Patient patient) {
        return prescriptionRepository.findAllPrescriptionsForPatient(patient);
    }

    public void removePrescription(MedicalPrescription prescription) {
        prescriptionRepository.removePrescription(prescription);
    }
}
