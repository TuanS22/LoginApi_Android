package com.example.loginapi.model;

import java.io.Serializable;

public class User implements Serializable {
    private String UserID;
    private String PassWord;

    public String getUserID() {
        return UserID;
    }

    public String getPassWord() {
        return PassWord;
    }

    @Override
    public String toString() {
        return "User{" +
                ", UserID='" + UserID + '\'' +
                ", PassWord='" + PassWord + '\'' +
                '}';
    }
}
