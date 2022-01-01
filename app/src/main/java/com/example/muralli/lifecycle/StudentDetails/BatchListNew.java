package com.example.muralli.lifecycle.StudentDetails;

import java.util.ArrayList;

public class BatchListNew {
    ArrayList<String> bList=new ArrayList<>();

    public BatchListNew(ArrayList<String> bList) {
        this.bList = bList;
    }

    public ArrayList<String> getbList() {
        return bList;
    }

    public void setbList(ArrayList<String> bList) {
        this.bList = bList;
    }
}
