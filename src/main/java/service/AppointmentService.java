package service;

import model.appointment.Appointment;
import model.user.Doctor;
import model.user.Patient;
import model.user.User;
import repositories.appointment.AppointmentRepository;

import java.util.Set;

public class AppointmentService {

    private AppointmentRepository appointmentRepository;

    private AppointmentService() {
        appointmentRepository = AppointmentRepository.build(AppointmentRepository.Type.DB);
    }

    public static AppointmentService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private final static class SingletonHolder {
        private static final AppointmentService INSTANCE = new AppointmentService();
    }

    public void addAppointment(Appointment appointment) {
        appointmentRepository.addAppointment(appointment);
    }

    public Set<Appointment> getAppointments(User user) {
        if (user instanceof Patient) {
            return appointmentRepository.getAppointmentsForPatient((Patient) user);
        }
        return appointmentRepository.getAppointmentsForDoctor((Doctor) user);
    }

    public void removeAppointment(Appointment appointment) {
        appointmentRepository.removeAppointment(appointment);
    }
}
