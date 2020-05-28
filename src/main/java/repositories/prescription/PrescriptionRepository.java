package repositories.prescription;

import exceptions.WrongRepositoryTypeException;
import model.medicalprescription.MedicalPrescription;
import model.user.Patient;

import java.util.List;

public interface PrescriptionRepository {

    void addPrescription(MedicalPrescription medicalPrescription);
    void removePrescription(MedicalPrescription medicalPrescription);
    List<MedicalPrescription> findAllPrescriptionsForPatient(Patient patient);

    static PrescriptionRepository build(Type type) {
        switch (type) {
            case FILE: return new FilePrescriptionRepository();
            case DB: return new DBPrescriptionRepository();
            case LIST: return new ListPrescriptionRepository();
        }
        throw new WrongRepositoryTypeException("prescription");
    }

    enum Type {
        FILE, DB, LIST
    }
}
