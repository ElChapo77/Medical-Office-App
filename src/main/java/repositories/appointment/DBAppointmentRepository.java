package repositories.appointment;

import managers.DBConnectionManager;
import model.appointment.Appointment;
import model.medicaloffice.MedicalOffice;
import model.user.Doctor;
import model.user.Patient;
import repositories.medicaloffice.MedicalOfficeRepository;
import repositories.user.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DBAppointmentRepository implements AppointmentRepository {

    @Override
    public void addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments VALUES (NULL, ?, ?, ?, ?, ?)";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setString(1, appointment.getPatient().getUsername());
            statement.setString(2, appointment.getMedicalOffice().getDoctorUsername());
            statement.setString(3, appointment.getMedicalOffice().getName());
            statement.setString(4, appointment.getMedicalOffice().getAddress());
            statement.setString(5, appointment.getDateAsString());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAppointment(Appointment appointment) {
        String sql = "DELETE FROM appointments WHERE patientUsername = ? and officeName = ? and officeAddress = ?";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setString(1, appointment.getPatient().getUsername());
            statement.setString(2, appointment.getMedicalOffice().getName());
            statement.setString(3, appointment.getMedicalOffice().getAddress());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Appointment> getAppointmentsForPatient(Patient patient) {
        String sql = "SELECT * FROM appointments WHERE patientUsername = ?";
        Set<Appointment> appointments = new TreeSet<>();

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setString(1, patient.getUsername());

            ResultSet set = statement.executeQuery();
            while (set.next()) {
                int id = set.getInt("appointment_id");
                String patientUsername = set.getString("patientUsername");
                String doctorUsername = set.getString("doctorUsername");
                String officeName = set.getString("officeName");
                String officeAddress = set.getString("officeAddress");
                var date =
                        Arrays.stream(set.getString("date").split("/"))
                                .map(Integer::parseInt)
                                .collect(Collectors.toList());

                var medicalOfficeRepository = MedicalOfficeRepository.build(MedicalOfficeRepository.Type.DB);
                MedicalOffice medicalOffice = null;
                for (var mo : medicalOfficeRepository.findMedicalOfficesByDoctor(doctorUsername)) {
                    if (mo.getName().equals(officeName) && mo.getAddress().equals(officeAddress)) {
                        medicalOffice = mo;
                        break;
                    }
                }

                appointments.add(new Appointment(patient, medicalOffice, date.get(0), date.get(1), date.get(2), date.get(3)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    @Override
    public Set<Appointment> getAppointmentsForDoctor(Doctor doctor) {
        String sql = "SELECT * FROM appointments WHERE doctorUsername = ?";
        Set<Appointment> appointments = new TreeSet<>();

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setString(1, doctor.getUsername());

            ResultSet set = statement.executeQuery();
            while (set.next()) {
                int id = set.getInt("appointment_id");
                String patientUsername = set.getString("patientUsername");
                String doctorUsername = set.getString("doctorUsername");
                String officeName = set.getString("officeName");
                String officeAddress = set.getString("officeAddress");
                var date =
                        Arrays.stream(set.getString("date").split("/"))
                                .map(Integer::parseInt)
                                .collect(Collectors.toList());

                var medicalOfficeRepository = MedicalOfficeRepository.build(MedicalOfficeRepository.Type.DB);
                MedicalOffice medicalOffice = null;
                for (var mo : medicalOfficeRepository.findMedicalOfficesByDoctor(doctorUsername)) {
                    if (mo.getName().equals(officeName) && mo.getAddress().equals(officeAddress)) {
                        medicalOffice = mo;
                        break;
                    }
                }

                var userRepository = UserRepository.build(UserRepository.Type.DB);
                var patient = userRepository.findPatientUserByUsername(patientUsername);

                appointments.add(new Appointment((Patient) patient.get(), medicalOffice, date.get(0), date.get(1), date.get(2), date.get(3)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }
}
