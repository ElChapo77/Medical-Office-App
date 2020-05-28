package repositories.appointment;

import exceptions.WrongRepositoryTypeException;
import model.appointment.Appointment;
import model.user.Doctor;
import model.user.Patient;

import java.util.Set;

public interface AppointmentRepository {

    void addAppointment(Appointment appointment);
    void removeAppointment(Appointment appointment);
    Set<Appointment> getAppointmentsForPatient(Patient patient);
    Set<Appointment> getAppointmentsForDoctor(Doctor doctor);

    static AppointmentRepository build(AppointmentRepository.Type type) {
        switch (type) {
            case FILE: return new FileAppointmentRepository();
            case DB: return new DBAppointmentRepository();
            case SET: return new SetAppointmentRepository();
        }

        throw new WrongRepositoryTypeException("appointment");
    }

    enum Type {
        FILE, DB, SET
    }
}
