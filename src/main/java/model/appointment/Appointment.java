package model.appointment;

import model.medicaloffice.MedicalOffice;
import model.user.Patient;

public class Appointment implements Comparable<Appointment> {

    private Patient patient;
    private MedicalOffice medicalOffice;
    private int year;
    private int month;
    private int day;
    private int hour;

    public Appointment(Patient patient, MedicalOffice medicalOffice, int year, int month, int day, int hour) {
        this.patient = patient;
        this.medicalOffice = medicalOffice;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
    }

    public Patient getPatient() {
        return patient;
    }

    public MedicalOffice getMedicalOffice() {
        return medicalOffice;
    }

    public String getDateAsString() {
        return year + "/" + month + "/" + day + "/" + hour;
    }

    @Override
    public int compareTo(Appointment o) {
        if(this.year != o.year)
            return this.year - o.year;

        if(this.month != o.month)
            return this.month - o.month;

        if(this.day != o.day)
            return this.day - o.day;

        return this.hour - o.hour;
    }

}
