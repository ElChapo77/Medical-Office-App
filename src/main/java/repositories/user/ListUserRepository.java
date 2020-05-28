package repositories.user;

import model.user.Doctor;
import model.user.Patient;
import model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListUserRepository implements UserRepository {

    private List<User> users;

    public ListUserRepository() {
        this.users = new ArrayList<>();
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public Optional<User> findDoctorUserByUsername(String username) {
        List<Doctor> doctors =
                users.stream()
                        .filter(x -> x instanceof Doctor)
                        .map(x -> (Doctor) x)
                        .filter(x -> x.getUsername().equals(username))
                        .collect(Collectors.toList());

        if (doctors.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(doctors.get(0));
    }

    @Override
    public Optional<User> findPatientUserByUsername(String username) {
        List<Patient> patients =
                users.stream()
                        .filter(x -> x instanceof Patient)
                        .map(x -> (Patient) x)
                        .filter(x -> x.getUsername().equals(username))
                        .collect(Collectors.toList());

        if (patients.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(patients.get(0));
    }
}
