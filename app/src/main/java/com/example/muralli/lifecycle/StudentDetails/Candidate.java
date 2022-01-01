package com.example.muralli.lifecycle.StudentDetails;

import java.util.HashMap;

/**
 * Created by Muralli on 18-04-2018.
 */
public class Candidate {
    String Rollno;
    Float cgpa;
    Float x;
    Float xii;
 HashMap per;

    public Candidate(String rollno, HashMap per, Float xii, Float x, Float cgpa) {
        Rollno = rollno;
        this.per = per;
        this.xii = xii;
        this.x = x;
        this.cgpa = cgpa;
    }

    public HashMap getPer() {
        return per;
    }

    public void setPer(HashMap per) {
        this.per = per;
    }

    public Float getXii() {
        return xii;
    }

    public void setXii(Float xii) {
        this.xii = xii;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public String getRollno() {
        return Rollno;
    }

    public void setRollno(String rollno) {
        Rollno = rollno;
    }

    public Float getCgpa() {
        return cgpa;
    }

    public void setCgpa(Float cgpa) {
        this.cgpa = cgpa;
    }
}
