package model.user;

public class Patient extends User {

    public Patient(String username, String password) {
        super(username, password);
    }

    public Patient(int id, String username, String password, String name, int age, boolean isMale) {
        super(id, username, password, name, age, isMale);
    }

}
