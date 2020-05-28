package repositories.user;

import exceptions.NonExistentFileException;
import model.user.Doctor;
import model.user.Patient;
import model.user.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class FileUserRepository implements UserRepository {

    private String userType(User user) {
        if (user instanceof Doctor)
            return "src/main/resources/org/nacu/files/DOCTORS";
        return "src/main/resources/org/nacu/files/PATIENTS";
    }

    @Override
    public void addUser(User user) {
        String file = userType(user);

        try {
            String str = user.getId() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getName() + "," + user.getAge() + "," + user.isMale() + "\n";
            Path path = Paths.get(file);
            Files.write(path, str.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findDoctorUserByUsername(String username) {
        return findUserByUsername(username, "src/main/resources/org/nacu/files/DOCTORS");
    }

    @Override
    public Optional<User> findPatientUserByUsername(String username) {
        return findUserByUsername(username, "src/main/resources/org/nacu/files/PATIENTS");
    }

    private Optional<User> findUserByUsername(String username, String file) {
        Path path = Paths.get(file);
        User user = null;

        try {
            if (!Files.exists(path)) {
                throw new NonExistentFileException(file);
            }

            var list = Files.readAllLines(path);
            for (var account : list) {
                String[] attr = account.split(",");
                if (attr[1].equals(username)) {
                    int id = Integer.parseInt(attr[0]);
                    String password = attr[2];
                    String name = attr[3];
                    int age = Integer.parseInt(attr[4]);
                    boolean isMale = Boolean.parseBoolean(attr[5]);
                    if (file.equals("src/main/resources/org/nacu/files/DOCTORS")) {
                        user = new Doctor(id, username, password, name, age, isMale);
                    } else {
                        user = new Patient(id, username, password, name, age, isMale);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(user);
    }

}
