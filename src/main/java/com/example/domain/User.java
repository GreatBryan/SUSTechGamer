package com.example.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="User")
public class User implements Serializable {
    @Id
    private int uid;

    @Column(name="name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "profile")
    private String profile;
    @Column(name = "balance")
    private double balance;
    @Column(name = "Rol")
    private int role;
    @Column(name = "sex")
    private char sex;

    public User(){super();
    }

    public User(int uid, String name, String password, String profile, double balance, int role, char sex) {
        this.uid = uid;
        this.name = name;
        this.password = password;
        this.profile = profile;
        this.balance = balance;
        this.role = role;
        this.sex = sex;
    }

    public int getIdUser() {
        return uid;
    }

    public void setIdUser(int idUser) {
        this.uid = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
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
}
