package com.example.muralli.lifecycle.StudentDetails;

/**
 * Created by Muralli on 25-04-2019.
 */
public class UserDetails {
    String uname;
    String lat;
    String longt;
    public UserDetails() {

    }

    public UserDetails(String uname, String lat, String longt) {
        this.uname = uname;
        this.lat = lat;
        this.longt = longt;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongt() {
        return longt;
    }

    public void setLongt(String longt) {
        this.longt = longt;
    }
}
