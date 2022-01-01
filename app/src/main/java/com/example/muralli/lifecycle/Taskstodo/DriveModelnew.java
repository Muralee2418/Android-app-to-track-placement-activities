package com.example.muralli.lifecycle.Taskstodo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muralli on 16-03-2018.
 */
public class DriveModelnew implements Comparable<DriveModelnew> {
    String compdate;
    int stNo,salary;
    String company;
    List<String> eligibleList=new ArrayList<>();

    public DriveModelnew(){}

    public DriveModelnew(List<String> eligibleList, String company, int stNo, String compdate,int salary) {
        this.eligibleList = eligibleList;
        this.company = company;
        this.stNo = stNo;
        this.compdate = compdate;
        this.salary=salary;
    }

   public String getCompdate() {

        /*Date date1= null;
       Log.d("check dt br frmt"+company,compdate);
        try {
            date1 = new SimpleDateFormat("mm/dd/yy").parse(compdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newFormat=new SimpleDateFormat("dd/mm/yy");
        String newDate=newFormat.format(date1);
       Log.d("check dt aftr frmt"+company,newDate);


        return newDate;*/
        return compdate;
    }

    public void setCompdate(String compdate) {
        this.compdate = compdate;
    }

    public List<String> getEligibleList() {
        return eligibleList;
    }

    public void setEligibleList(List<String> eligibleList) {
        this.eligibleList = eligibleList;
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

        /*Date date1= null;
        try {
            date1 = new SimpleDateFormat("MM/dd/yy", Locale.US).parse(compdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newFormat=new SimpleDateFormat("dd/MM/yyyy",Locale.UK);
        String newDate=newFormat.format(date1);
        return newDate;*/
        return compdate;
    }

    /*public void setDate(String date) {
        compdate = date;
    }*/

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void addCandidate(String roll){
        if(!this.eligibleList.contains(roll))
        {
            this.eligibleList.add(roll);
        }
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    /*public Date getDateinFormat()
    {
        String sDate1=compdate;
        try {
            Date date1=new SimpleDateFormat("mm/dd/yy").parse(sDate1);
            SimpleDateFormat newFormat=new SimpleDateFormat("dd/mm/yyyy");
            String newDate=newFormat.format(date1);
            Date date2=new SimpleDateFormat("dd/mm/yyyy").parse(newDate);
            return date2;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }*/


    @Override
    public int compareTo(DriveModelnew another) {
        /*if(getDateinFormat()==null||another.getDateinFormat()==null)
            return 0;
        return getDateinFormat().compareTo(another.getDateinFormat());*/
        return 0;
    }
}

