package com.example.muralli.lifecycle.Taskstodo;

/**
 * Created by Muralli on 16-03-2018.
 */
public class DriveModel {
    String compdate;
    int stNo;
    String company;

    public DriveModel(){}

    public DriveModel(String comdate, int state, String company) {
        compdate = comdate;
        this.stNo = state;
        this.company = company;
    }

    public int getStNo() {
        return stNo;
    }

    public void setStNo(int stNo) {
        this.stNo = stNo;
    }
    /* public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }*/

    public String getDate() {
        return compdate;
    }

    public void setDate(String date) {
        compdate = date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
