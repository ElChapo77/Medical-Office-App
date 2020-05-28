package repositories.medicaloffice;

import exceptions.NonExistentFileException;
import model.medicaloffice.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileMedicalOfficeRepository implements MedicalOfficeRepository {

    private final String file = "src/main/resources/org/nacu/files/MEDICAL_OFFICES";

    @Override
    public void addMedicalOffice(MedicalOffice medicalOffice) {
        try {
            String str = medicalOffice.getDoctorUsername() + "," + medicalOffice.getName() + "," + medicalOffice.getAddress() + "\n";
            Path path = Paths.get(file);
            Files.write(path, str.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MedicalOffice> findMedicalOfficesByDoctor(String username) {
        Path path = Paths.get(file);
        List<MedicalOffice> medicalOffices = new ArrayList<>();

        try {
            if (!Files.exists(path)) {
                throw new NonExistentFileException(file);
            }
            var list = Files.readAllLines(path);
            for (String office : list) {
                String[] attr = office.split(",");
                if (attr[0].equals(username)) {
                    MedicalOffice medicalOffice;
                    switch (attr[1]) {
                        case "Dental Office":
                            medicalOffice = new DentalOffice(username, attr[2]);
                            break;
                        case "Dermatological Office":
                            medicalOffice = new DermatologicalOffice(username, attr[2]);
                            break;
                        case "Ophthalmic Office":
                            medicalOffice = new OphthalmicOffice(username, attr[2]);
                            break;
                        default:
                            medicalOffice = new OrthopedicOffice(username, attr[2]);
                    }
                    medicalOffices.add(medicalOffice);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return medicalOffices;
    }

    @Override
    public List<MedicalOffice> getAllMedicalOffices() {
        Path path = Paths.get(file);
        List<MedicalOffice> medicalOffices = new ArrayList<>();

        try {
            if (!Files.exists(path)) {
                throw new NonExistentFileException(file);
            }
            var list = Files.readAllLines(path);
            for (String office : list) {
                String[] attr = office.split(",");
                MedicalOffice medicalOffice;
                switch (attr[1]) {
                    case "Dental Office":
                        medicalOffice = new DentalOffice(attr[0], attr[2]);
                        break;
                    case "Dermatological Office":
                        medicalOffice = new DermatologicalOffice(attr[0], attr[2]);
                        break;
                    case "Ophthalmic Office":
                        medicalOffice = new OphthalmicOffice(attr[0], attr[2]);
                        break;
                    default:
                        medicalOffice = new OrthopedicOffice(attr[0], attr[2]);
                }
                medicalOffices.add(medicalOffice);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return medicalOffices;
    }
}
