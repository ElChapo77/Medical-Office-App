package repositories.appointment;

import model.appointment.Appointment;
import model.user.Doctor;
import model.user.Patient;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class SetAppointmentRepository implements AppointmentRepository {

    private Set<Appointment> appointments;

    public SetAppointmentRepository() {
        this.appointments = new TreeSet<>();
    }

    @Override
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    @Override
    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }

    @Override
    public Set<Appointment> getAppointmentsForPatient(Patient patient) {
        return appointments.stream()
                .filter(x -> x.getPatient().getUsername().equals(patient.getUsername()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Appointment> getAppointmentsForDoctor(Doctor doctor) {
        return appointments.stream()
                .filter(x -> x.getPatient().getUsername().equals(doctor.getUsername()))
                .collect(Collectors.toSet());
    }
}
