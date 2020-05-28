package repositories.medicaloffice;

import managers.DBConnectionManager;
import model.medicaloffice.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBMedicalOfficeRepository implements MedicalOfficeRepository {

    @Override
    public void addMedicalOffice(MedicalOffice medicalOffice) {
        String sql = "INSERT INTO medical_offices VALUES (NULL, ?, ?, ?)";

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setString(1, medicalOffice.getDoctorUsername());
            statement.setString(2, medicalOffice.getName());
            statement.setString(3, medicalOffice.getAddress());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MedicalOffice> findMedicalOfficesByDoctor(String username) {
        String sql = "SELECT * FROM medical_offices WHERE doctorUsername = ?";
        List<MedicalOffice> medicalOffices = new ArrayList<>();

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            statement.setString(1, username);

            ResultSet set = statement.executeQuery();
            while (set.next()) {
                int id = set.getInt("medical_office_id");
                String doctorUsername = set.getString("doctorUsername");
                String name = set.getString("name");
                String address = set.getString("address");

                MedicalOffice medicalOffice;
                switch (name) {
                    case "Dental Office":
                        medicalOffice = new DentalOffice(doctorUsername, address);
                        break;
                    case "Dermatological Office":
                        medicalOffice = new DermatologicalOffice(doctorUsername, address);
                        break;
                    case "Ophthalmic Office":
                        medicalOffice = new OphthalmicOffice(doctorUsername, address);
                        break;
                    default:
                        medicalOffice = new OrthopedicOffice(doctorUsername, address);
                }
                medicalOffices.add(medicalOffice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicalOffices;
    }

    @Override
    public List<MedicalOffice> getAllMedicalOffices() {
        String sql = "SELECT * FROM medical_offices";
        List<MedicalOffice> medicalOffices = new ArrayList<>();

        try (
                Connection con = DBConnectionManager.getInstance().createConnection();
                PreparedStatement statement = con.prepareStatement(sql);
        ) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                int id = set.getInt("medical_office_id");
                String doctorUsername = set.getString("doctorUsername");
                String name = set.getString("name");
                String address = set.getString("address");

                MedicalOffice medicalOffice;
                switch (name) {
                    case "Dental Office":
                        medicalOffice = new DentalOffice(doctorUsername, address);
                        break;
                    case "Dermatological Office":
                        medicalOffice = new DermatologicalOffice(doctorUsername, address);
                        break;
                    case "Ophthalmic Office":
                        medicalOffice = new OphthalmicOffice(doctorUsername, address);
                        break;
                    default:
                        medicalOffice = new OrthopedicOffice(doctorUsername, address);
                }
                medicalOffices.add(medicalOffice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicalOffices;
    }
}
