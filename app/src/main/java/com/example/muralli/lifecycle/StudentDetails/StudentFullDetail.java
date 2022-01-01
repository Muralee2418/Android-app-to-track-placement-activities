package com.example.muralli.lifecycle.StudentDetails;

/**
 * Created by Muralli on 20-02-2018.
 */
public class StudentFullDetail {
    String rollno;
    String name;
    String gender;
    String fastrackdetails;
    String placementstatus;
    String placementcompany;
    String internshipcompany;
    String aoi;
    String technical_expertize;
    Long arear;
    Long history;
    Long x;
    Long xii;
    Long diplamo;
    Long cgpa;
    String email;
    Long phone;

    public StudentFullDetail(){}

    public StudentFullDetail(String rollno, String Internshipcompany, String placementcompany, String placementstatus, String fastrackdetails, String gender, String name, Long x, Long xii, Long diplamo, Long cgpa, String email, Long phone, Long arear, Long history, String tech_expertz, String aoi) {
        this.rollno = rollno;
        this.internshipcompany = Internshipcompany;
        this.placementcompany = placementcompany;
        this.placementstatus = placementstatus;
        this.fastrackdetails = fastrackdetails;
        this.arear=arear;
        this.history=history;
        this.gender = gender;
        this.name = name;
        this.aoi=aoi;
        this.technical_expertize=tech_expertz;
        this.x=x;
        this.xii=xii;
        this.diplamo=diplamo;
        this.cgpa=cgpa;
        this.email=email;
        this.phone=phone;
    }

    public String getAoi() {
        return aoi;
    }

    public void setAoi(String aoi) {
        this.aoi = aoi;
    }

    public String getTechnical_expertize() {
        return technical_expertize;
    }

    public void setTechnical_expertize(String technical_expertize) {
        this.technical_expertize = technical_expertize;
    }

    public Long getArear() {
        return arear;
    }

    public void setArear(Long arear) {
        this.arear = arear;
    }

    public Long getHistory() {
        return history;
    }

    public void setHistory(Long history) {
        this.history = history;
    }

    public String getInternshipcompany() {
        return internshipcompany;
    }

    public void setInternshipcompany(String internshipcompany) {
        this.internshipcompany = internshipcompany;
    }

    public String getPlacementcompany() {
        return placementcompany;
    }

    public void setPlacementcompany(String placementcompany) {
        this.placementcompany = placementcompany;
    }

    public String getPlacementstatus() {
        return placementstatus;
    }

    public void setPlacementstatus(String placementstatus) {
        this.placementstatus = placementstatus;
    }

    public String getFastrackdetails() {
        return fastrackdetails;
    }

    public void setFastrackdetails(String fastrackdetails) {
        this.fastrackdetails = fastrackdetails;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getXii() {
        return xii;
    }

    public void setXii(Long xii) {
        this.xii = xii;
    }

    public Long getDiplamo() {
        return diplamo;
    }

    public void setDiplamo(Long diplamo) {
        this.diplamo = diplamo;
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

    public Long getCgpa() {
        return cgpa;
    }

    public void setCgpa(Long cgpa) {
        this.cgpa = cgpa;
    }

}
