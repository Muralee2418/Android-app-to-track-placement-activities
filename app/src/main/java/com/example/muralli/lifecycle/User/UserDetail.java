package com.example.muralli.lifecycle.User;

/**
 * Created by Muralli on 05-06-2018.
 */
public class UserDetail {
    String email;
    String name;
    String role;

    public UserDetail() {
    }

    public UserDetail(String email, String role, String name) {
        this.email = email;
        this.role = role;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
