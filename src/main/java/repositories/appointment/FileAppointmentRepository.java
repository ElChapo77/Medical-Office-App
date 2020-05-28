package repositories.appointment;

import exceptions.NonExistentFileException;
import model.appointment.Appointment;
import model.medicaloffice.MedicalOffice;
import model.user.Doctor;
import model.user.Patient;
import repositories.user.UserRepository;
import service.MedicalOfficeService;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class FileAppointmentRepository implements AppointmentRepository {

    private final String file = "src/main/resources/org/nacu/files/APPOINTMENTS";

    @Override
    public void addAppointment(Appointment appointment) {
        try {
            String str = appointment.getMedicalOffice().getDoctorUsername() + "," + appointment.getMedicalOffice().getName() + "," + appointment.getMedicalOffice().getAddress() + "," + appointment.getPatient().getUsername() + "," + appointment.getDateAsString() + "\n";
            Path path = Paths.get(file);
            Files.write(path, str.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAppointment(Appointment appointment) {
        Path path = Paths.get(file);
        String appointmentString = appointment.getMedicalOffice().getDoctorUsername() + "," + appointment.getMedicalOffice().getName() + "," + appointment.getMedicalOffice().getAddress() + "," + appointment.getPatient().getUsername() + "," + appointment.getDateAsString();

        try {
            if (!Files.exists(path)) {
                throw new NonExistentFileException(file);
            }
            var list =
                    Files.readAllLines(path).stream()
                            .filter(x -> !x.equals(appointmentString))
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
    public Set<Appointment> getAppointmentsForPatient(Patient patient) {
        Path path = Paths.get(file);
        Set<Appointment> appointments = new TreeSet<>();

        try {
            if (!Files.exists(path)) {
                throw new NonExistentFileException(file);
            }
            var list = Files.readAllLines(path);
            for (var appointment : list) {
                String[] attr = appointment.split(",");
                if (attr[3].equals(patient.getUsername())) {
                    var date =
                            Arrays.stream(attr[4].split("/"))
                                    .map(Integer::parseInt)
                                    .collect(Collectors.toList());

                    MedicalOffice medicalOffice = null;
                    MedicalOfficeService medicalOfficeService = MedicalOfficeService.getInstance();
                    List<MedicalOffice> medicalOffices = medicalOfficeService.findMedicalOffices(attr[0]);
                    for (var mo : medicalOffices) {
                        if (mo.getName().equals(attr[1]) && mo.getAddress().equals(attr[2])) {
                            medicalOffice = mo;
                            break;
                        }
                    }

                    appointments.add(new Appointment(patient, medicalOffice, date.get(0), date.get(1), date.get(2), date.get(3)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    @Override
    public Set<Appointment> getAppointmentsForDoctor(Doctor doctor) {
        Path path = Paths.get(file);
        Set<Appointment> appointments = new TreeSet<>();

        try {
            if (!Files.exists(path)) {
                throw new NonExistentFileException(file);
            }
            var list = Files.readAllLines(path);
            for (var appointment : list) {
                String[] attr = appointment.split(",");
                if (attr[0].equals(doctor.getUsername())) {
                    var date =
                            Arrays.stream(attr[4].split("/"))
                                    .map(Integer::parseInt)
                                    .collect(Collectors.toList());

                    MedicalOffice medicalOffice = null;
                    MedicalOfficeService medicalOfficeService = MedicalOfficeService.getInstance();
                    List<MedicalOffice> medicalOffices = medicalOfficeService.findMedicalOffices(attr[0]);
                    for (var mo : medicalOffices) {
                        if (mo.getName().equals(attr[1]) && mo.getAddress().equals(attr[2])) {
                            medicalOffice = mo;
                            break;
                        }
                    }

                    var userRepository = UserRepository.build(UserRepository.Type.FILE);
                    appointments.add(new Appointment((Patient) userRepository.findPatientUserByUsername(attr[3]).get(), medicalOffice, date.get(0), date.get(1), date.get(2), date.get(3)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return appointments;
    }
}
