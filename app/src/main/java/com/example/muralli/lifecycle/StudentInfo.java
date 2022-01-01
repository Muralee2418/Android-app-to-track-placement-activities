package com.example.muralli.lifecycle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

/**
 * Created by Muralli on 16-02-2018.
 */
public class StudentInfo {
    String s_roll, s_name, s_gender, s_FastrackDetails, s_PlacementStatus, s_PlacementCompany, s_InternshipCompany, s_GoneForInternship, s_IdentifiedorNot;
   // int s_Area;


    public StudentInfo(){}
    public StudentInfo(String roll,String fastrackDetails,String gender,String goneForInternship,String identifiedorNot,String internshipCompany,String name,String placementCompany,String placementStatus){
        s_roll=roll;
        s_name=name;
        s_gender=gender;
        s_FastrackDetails=fastrackDetails;
        s_PlacementCompany=placementCompany;
        s_PlacementStatus=placementStatus;

        s_InternshipCompany=internshipCompany;
        s_GoneForInternship=goneForInternship;
        s_IdentifiedorNot=identifiedorNot;

    }

    public String getS_roll() {
        return s_roll;
    }

    public String getS_name() {
        return s_name;
    }

    public String getS_gender() {
        return s_gender;
    }

    public String getS_FastrackDetails() {
        return s_FastrackDetails;
    }

    public String getS_PlacementCompany() {
        return s_PlacementCompany;
    }

   /* public int getS_Arear() {
        return s_Arear;
    }*/

    public String getS_InternshipCompany() {
        return s_InternshipCompany;
    }

    public String getS_PlacementStatus() {
        return s_PlacementStatus;
    }

    public String getS_GoneForInternship() {
        return s_GoneForInternship;
    }

    public String getS_IdentifiedorNot() {
        return s_IdentifiedorNot;
    }

    /**
     * Created by Muralli on 29-05-2018.
     */

}
