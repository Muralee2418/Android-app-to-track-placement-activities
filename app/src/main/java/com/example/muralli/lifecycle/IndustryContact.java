package com.example.muralli.lifecycle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muralli on 21-01-2018.
 */
public class IndustryContact {
    String expert_Name;
    String expert_Mobile;
    String expert_Company;
    List<String> expert_Viewers =new ArrayList<>();

    public IndustryContact(){}

    public IndustryContact(String eName,String eNo,String eCompany, List<String> expert_viewers){
        expert_Name=eName;
        expert_Mobile=eNo;
        expert_Company=eCompany;
        expert_Viewers = expert_viewers;
    }

    public String getExpert_Company() {
        return expert_Company;
    }

    public String getExpert_Mobile() {
        return expert_Mobile;
    }

    public String getExpert_Name() {
        return expert_Name;
    }

    public List<String> getExpert_Viewers() {
        return expert_Viewers;
    }

    public Boolean checkViewers(String v_id){
        if(expert_Viewers.contains(v_id)){return true;}
        else{return false;}
    }
}
