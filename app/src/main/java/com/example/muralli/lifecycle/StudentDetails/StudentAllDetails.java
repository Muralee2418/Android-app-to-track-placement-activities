package com.example.muralli.lifecycle.StudentDetails;

/**
 * Created by Muralli on 22-05-2018.
 */
public class StudentAllDetails {
    String rollno;
    String name;
    String gender;
    String fastrackdetails;
    String placementstatus;
    String placementcompany;
    String internshipcompany;
    String intern_from;
    String intern_to;
    String intern_location;
    int intern_stiphend;
    Float x;
    Float xii;
    Float diplamo;
    Float cgpa;
    int history;
    int arear;
    String email;
    Long phone;
    String dob;
    String skill_set;
    String aoi;
    public StudentAllDetails(){}

    public StudentAllDetails(String rollno, String name, String gender, String fastrackdetails, String placementstatus, String placementcompany, String internshipcompany, String intern_from, String intern_to, String intern_location, int intern_stiphend, Float x, Float xii, Float diplamo, Float cgpa, int history, int arear, String email, Long phone, String dob, String skill_set, String aoi) {
        this.rollno = rollno;
        this.name = name;
        this.gender = gender;
        this.fastrackdetails = fastrackdetails;
        this.placementstatus = placementstatus;
        this.placementcompany = placementcompany;
        this.internshipcompany = internshipcompany;
        this.intern_from = intern_from;
        this.intern_to = intern_to;
        this.intern_location = intern_location;
        this.intern_stiphend = intern_stiphend;
        this.x = x;
        this.xii = xii;
        this.diplamo = diplamo;
        this.cgpa = cgpa;
        this.history = history;
        this.arear = arear;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.skill_set = skill_set;
        this.aoi = aoi;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFastrackdetails() {
        return fastrackdetails;
    }

    public void setFastrackdetails(String fastrackdetails) {
        this.fastrackdetails = fastrackdetails;
    }

    public String getPlacementstatus() {
        return placementstatus;
    }

    public void setPlacementstatus(String placementstatus) {
        this.placementstatus = placementstatus;
    }

    public String getPlacementcompany() {
        return placementcompany;
    }

    public void setPlacementcompany(String placementcompany) {
        this.placementcompany = placementcompany;
    }

    public String getInternshipcompany() {
        return internshipcompany;
    }

    public void setInternshipcompany(String internshipcompany) {
        this.internshipcompany = internshipcompany;
    }

    public String getIntern_from() {
        return intern_from;
    }

    public void setIntern_from(String intern_from) {
        this.intern_from = intern_from;
    }

    public String getIntern_to() {
        return intern_to;
    }

    public void setIntern_to(String intern_to) {
        this.intern_to = intern_to;
    }

    public String getIntern_location() {
        return intern_location;
    }

    public void setIntern_location(String intern_location) {
        this.intern_location = intern_location;
    }

    public int getIntern_stiphend() {
        return intern_stiphend;
    }

    public void setIntern_stiphend(int intern_stiphend) {
        this.intern_stiphend = intern_stiphend;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getXii() {
        return xii;
    }

    public void setXii(Float xii) {
        this.xii = xii;
    }

    public Float getDiplamo() {
        return diplamo;
    }

    public void setDiplamo(Float diplamo) {
        this.diplamo = diplamo;
    }

    public Float getCgpa() {
        return cgpa;
    }

    public void setCgpa(Float cgpa) {
        this.cgpa = cgpa;
    }

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }

    public int getArear() {
        return arear;
    }

    public void setArear(int arear) {
        this.arear = arear;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSkill_set() {
        return skill_set;
    }

    public void setSkill_set(String skill_set) {
        this.skill_set = skill_set;
    }

    public String getAoi() {
        return aoi;
    }

    public void setAoi(String aoi) {
        this.aoi = aoi;
    }

    @Override
    public String toString() {
        return rollno+name+email;
    }
}
