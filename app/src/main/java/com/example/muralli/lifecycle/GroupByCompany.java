package com.example.muralli.lifecycle;

import com.example.muralli.lifecycle.StudentDetails.StudentAllDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muralli on 06-03-2018.
 */
public class GroupByCompany {
    List<StudentAllDetails> studentlist;
    String companyName;
    int ctc;

    public String getCompanyName() {
        return companyName;
    }

    public GroupByCompany(List<StudentAllDetails> studentlist, String companyName) {
        this.studentlist = studentlist;
        this.companyName = companyName;
    }

    public int getCtc() {
        return ctc;
    }

    public void setCtc(int ctc) {
        this.ctc = ctc;
    }

    public int studentCount() {
        int count=0;
        String firstComp;
        String secComp;
        String studComp;
        for (StudentAllDetails student : studentlist) {
            studComp=student.getPlacementcompany();
            if(studComp.contains("$")) {

                secComp=studComp.substring(studComp.indexOf("$")+1);
                firstComp=studComp.substring(0,studComp.indexOf("$"));
                if(firstComp.equalsIgnoreCase(companyName)){
                    count++;
                }
                if(secComp.equalsIgnoreCase(companyName)){
                    count++;
                }
            }
            else{
                firstComp=student.getPlacementcompany();
                if(firstComp.equalsIgnoreCase(companyName)){
                    count++;
                }
                secComp="0";
            }


        }
        return count;
    }
    public List<StudentAllDetails> studentPlaced(){
        List<StudentAllDetails> newList = new ArrayList<StudentAllDetails>();
        String firstComp;
        String secComp;
        String studComp;
        int i=0;

        for (StudentAllDetails student : studentlist) {
            studComp=student.getPlacementcompany();

            if(studComp.contains("$")) {

                secComp=studComp.substring(studComp.indexOf("$")+1);
                firstComp=studComp.substring(0,studComp.indexOf("$"));
                if(firstComp.equalsIgnoreCase(companyName)){
                    newList.add(student);
                }
                if(secComp.equalsIgnoreCase(companyName)){
                    newList.add(student);
                }
            }
            else{
                firstComp=student.getPlacementcompany();
                if(firstComp.equalsIgnoreCase(companyName)){
                    newList.add(student);
                }
               // secComp="0";
            }
            //Log.d("Check std List",newList.get(i).getName());

        }
        return newList;
    }

       }
