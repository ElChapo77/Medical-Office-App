package repositories.prescription;

import exceptions.NonExistentFileException;
import model.medicalprescription.MedicalPrescription;
import model.medicine.Aspirin;
import model.medicine.Medicine;
import model.medicine.Paracetamol;
import model.user.Patient;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilePrescriptionRepository implements PrescriptionRepository {

    private final String file = "src/main/resources/org/nacu/files/PRESCRIPTIONS";

    @Override
    public void addPrescription(MedicalPrescription medicalPrescription) {
        try {
            Path path = Paths.get(file);
            String str = medicalPrescription.getPatientUsername() + "," + medicalPrescription.getDescription() + ",";
            for (var medicine : medicalPrescription.getMedicines()) {
                str +=  medicine.getName() + " ";
            }
            str += "\n";
            Files.write(path, str.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removePrescription(MedicalPrescription medicalPrescription) {
        String prescriptionString = medicalPrescription.getPatientUsername() + "," + medicalPrescription.getDescription() + ",";
        for (var medicine : medicalPrescription.getMedicines()) {
            prescriptionString += medicine.getName() + " ";
        }

        try {
            Path path = Paths.get(file);
            if (!Files.exists(path)) {
                throw new NonExistentFileException(file);
            }

            String finalStr = prescriptionString;
            var list =
                    Files.readAllLines(path).stream()
                            .map(x -> x + " ")
                            .filter(x -> !x.equals(finalStr))
                            .map(x -> x + "\n")
                            .collect(Collectors.toList());

            PrintWriter pw = new PrintWriter(file);
            pw.close();
            for (var x : list) {
                Files.write(path, x.getBytes(), StandardOpenOption.APPEND);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MedicalPrescription> findAllPrescriptionsForPatient(Patient patient) {
        List<MedicalPrescription> medicalPrescriptions = new ArrayList<>();

        try {
            Path path = Paths.get(file);
            if (!Files.exists(path)) {
                throw new NonExistentFileException(file);
            }

            var list = Files.readAllLines(path);
            for (var line : list) {
                var attr = line.split(",");
                if (attr[0].equals(patient.getUsername())) {
                    List<Medicine> medicines =
                            Arrays.stream(attr[2].split(" "))
                                    .map(x -> {
                                        switch (x) {
                                            case "Aspirin":
                                                return new Aspirin(x);
                                            case "Paracetamol":
                                                return new Paracetamol(x);
                                            default:
                                                return null;
                                        }
                                    })
                                    .collect(Collectors.toList());
                    medicalPrescriptions.add(new MedicalPrescription(attr[0], medicines, attr[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return medicalPrescriptions;
    }

}
