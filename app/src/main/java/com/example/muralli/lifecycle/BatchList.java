package com.example.muralli.lifecycle;

import java.util.ArrayList;

public class BatchList {
    ArrayList<String> bList=new ArrayList<>();
    public BatchList(){}

    public BatchList(ArrayList<String> bList) {
        this.bList = bList;
    }

    public ArrayList<String> getbList() {
        return bList;
    }

    public void setbList(ArrayList<String> bList) {
        this.bList = bList;
    }
}
