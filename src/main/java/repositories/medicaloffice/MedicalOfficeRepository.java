package repositories.medicaloffice;

import exceptions.WrongRepositoryTypeException;
import model.medicaloffice.MedicalOffice;

import java.util.List;

public interface MedicalOfficeRepository {

    void addMedicalOffice(MedicalOffice medicalOffice);
    List<MedicalOffice> findMedicalOfficesByDoctor(String username);
    List<MedicalOffice> getAllMedicalOffices();

    static MedicalOfficeRepository build(Type type) {
        switch (type) {
            case FILE: return new FileMedicalOfficeRepository();
            case DB: return new DBMedicalOfficeRepository();
            case LIST: return new ListMedicalOfficeRepository();
        }

        throw new WrongRepositoryTypeException("medical office");
    }

    enum Type {
        FILE, DB, LIST
    }
}
