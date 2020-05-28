package repositories.user;

import exceptions.WrongRepositoryTypeException;
import model.user.User;

import java.util.Optional;

public interface UserRepository {

    void addUser(User user);
    Optional<User> findDoctorUserByUsername(String username);
    Optional<User> findPatientUserByUsername(String username);

    static UserRepository build(Type type) {
        switch (type) {
            case FILE: return new FileUserRepository();
            case DB: return new DBUserRepository();
            case LIST: return new ListUserRepository();
        }

        throw new WrongRepositoryTypeException("user");
    }

    enum Type {
        FILE, DB, LIST
    }
}
