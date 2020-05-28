package repositories.user;

import managers.DBConnectionManager;
import model.user.Doctor;
import model.user.Patient;
import model.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DBUserRepository implements UserRepository {

    @Override
    public void addUser(User user) {
        String table = user instanceof Doctor ? "doctors" : "patients";
        String sql = "INSERT INTO " + table + " VALUES (NULL, ?, ?, ?, ?, ?)";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, String.valueOf(user.getAge()));
            statement.setString(5, String.valueOf(user.isMale() ? 1 : 0));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findDoctorUserByUsername(String username) {
        return findUserByUsername(username, "doctors");
    }

    @Override
    public Optional<User> findPatientUserByUsername(String username) {
        return findUserByUsername(username, "patients");
    }

    private Optional<User> findUserByUsername(String username, String table) {
        String sql = "SELECT * FROM " + table + " WHERE username = ?";

        try (
            Connection con = DBConnectionManager.getInstance().createConnection();
            PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setString(1, username);

            ResultSet set = statement.executeQuery();
            if(set.next()) {
                int id = set.getInt("id");
                String u = set.getString("username");
                String password = set.getString("password");
                String name = set.getString("name");
                int age = set.getInt("age");
                boolean isMale = set.getBoolean("isMale");

                if (table.equals("doctors")) {
                    return Optional.of(new Doctor(id, u, password, name, age, isMale));
                }
                return Optional.of(new Patient(id, u, password, name, age, isMale));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
