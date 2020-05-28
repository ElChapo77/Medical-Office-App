package service;

import model.user.Doctor;
import model.user.User;
import repositories.user.UserRepository;

import java.util.Optional;

public class LoginService {

    private UserRepository userRepository;

    private LoginService() {
        userRepository = UserRepository.build(UserRepository.Type.DB);
    }

    public static LoginService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private final static class SingletonHolder {
        private final static LoginService INSTANCE = new LoginService();
    }

    public void register(User user) {
        userRepository.addUser(user);
    }

    public boolean login(User user) {
        Optional<User> result;
        if (user instanceof Doctor) {
            result = userRepository.findDoctorUserByUsername(user.getUsername());
        } else {
            result = userRepository.findPatientUserByUsername(user.getUsername());
        }

        if (result.isPresent()) {
            User u = result.get();
            return u.getPassword().equals(user.getPassword());
        }
        return false;
    }

}
