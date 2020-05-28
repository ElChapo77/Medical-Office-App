package model.medicaloffice;

public abstract class MedicalOffice {

    private String doctorUsername;
    private String name;
    private String address;

    public MedicalOffice(String doctorUsername, String name, String address) {
        this.doctorUsername = doctorUsername;
        this.name = name;
        this.address = address;
    }

    public String getDoctorUsername() {
        return doctorUsername;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
}
