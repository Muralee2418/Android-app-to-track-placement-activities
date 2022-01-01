package com.example.muralli.lifecycle.StudentDetails;

/**
 * Created by Muralli on 20-02-2018.
 */
public class StudentDetail {
    String rollno;
    String name;
    String gender;
    String fastrackdetails;
    String placementstatus;
    String placementcompany;
    String Internshipcompany;
    Long x;
    Long xii;
    Long diplamo;
    Long cgpa;
    String email;
    Long phone;

    public StudentDetail(){}

    public StudentDetail(String rollno, String Internshipcompany, String placementcompany, String placementstatus, String fastrackdetails, String gender, String name, Long x, Long xii, Long diplamo, Long cgpa, String email, Long phone) {
        this.rollno = rollno;
        this.Internshipcompany = Internshipcompany;
        this.placementcompany = placementcompany;
        this.placementstatus = placementstatus;
        this.fastrackdetails = fastrackdetails;
        this.gender = gender;
        this.name = name;
        this.x=x;
        this.xii=xii;
        this.diplamo=diplamo;
        this.cgpa=cgpa;
        this.email=email;
        this.phone=phone;
    }

    public String getInternshipcompany() {
        return Internshipcompany;
    }

    public void setInternshipcompany(String internshipcompany) {
        this.Internshipcompany = internshipcompany;
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
