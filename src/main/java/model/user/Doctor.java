package model.user;

public class Doctor extends User {

    public Doctor(String username, String password) {
        super(username, password);
    }

    public Doctor(int id, String username, String password, String name, int age, boolean isMale) {
        super(id, username, password, name, age, isMale);
    }

}
