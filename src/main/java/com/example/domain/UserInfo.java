package com.example.domain;

public class UserInfo {
    private int uid;
    private String userName;
    private double balance;
    private int role;
    private char sex;
    private String intro;
    private String birthday;

    public UserInfo(int uid, String userName, double balance, int role, char sex, String intro, String birthday) {
        this.uid = uid;
        this.userName = userName;
        this.balance = balance;
        this.role = role;
        this.sex = sex;
        this.intro = intro;
        this.birthday = birthday;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}

