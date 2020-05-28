package service;

import model.medicaloffice.MedicalOffice;
import repositories.medicaloffice.MedicalOfficeRepository;

import java.util.List;

public class MedicalOfficeService {

    private MedicalOfficeRepository medicalOfficeRepository;

    private MedicalOfficeService() {
        medicalOfficeRepository = MedicalOfficeRepository.build(MedicalOfficeRepository.Type.DB);
    }

    public static MedicalOfficeService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private final static class SingletonHolder {
        private final static MedicalOfficeService INSTANCE = new MedicalOfficeService();
    }

    public void addMedicalOffice(MedicalOffice medicalOffice) {
        medicalOfficeRepository.addMedicalOffice(medicalOffice);
    }

    public List<MedicalOffice> findMedicalOffices(String username) {
        return medicalOfficeRepository.findMedicalOfficesByDoctor(username);
    }

    public List<MedicalOffice> getAllMedicalOffices() {
        return medicalOfficeRepository.getAllMedicalOffices();
    }
}
