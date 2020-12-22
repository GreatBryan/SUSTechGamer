package com.example.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(ugdKey.class)
@Table(name = "user_game_dlc")
public class User_game_dlc implements Serializable {
    @Id
    @Column(name = "uid")
    private int uid;
    @Id
    @Column(name = "gid")
    private int gid;
    @Id
    @Column(name = "dlc")
    private int dlc;
    @Column(name = "save_path")
    private String save;
    @Column(name = "price")
    private double price;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User_game_dlc(){super();}

    public User_game_dlc(int uid, int gid, int dlc, String save) {
        this.uid = uid;
        this.gid = gid;
        this.dlc = dlc;
        this.save = save;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getDlc() {
        return dlc;
    }

    public void setDlc(int dlc) {
        this.dlc = dlc;
    }

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }
}
