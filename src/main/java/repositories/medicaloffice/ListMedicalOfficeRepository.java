package repositories.medicaloffice;

import model.medicaloffice.MedicalOffice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListMedicalOfficeRepository implements MedicalOfficeRepository {

    private List<MedicalOffice> medicalOffices;

    public ListMedicalOfficeRepository() {
        this.medicalOffices = new ArrayList<>();
    }

    @Override
    public void addMedicalOffice(MedicalOffice medicalOffice) {
        medicalOffices.add(medicalOffice);
    }

    @Override
    public List<MedicalOffice> findMedicalOfficesByDoctor(String username) {
        return medicalOffices.stream()
                .filter(x -> x.getDoctorUsername().equals(username))
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalOffice> getAllMedicalOffices() {
        return medicalOffices;
    }
}
