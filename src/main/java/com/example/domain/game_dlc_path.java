package com.example.domain;

import javax.persistence.*;

@Entity
@Table(name = "game_dlc_path")
public class game_dlc_path {
    @Column(name = "gid")
    private int gid;
    @Column(name = "did")
    private int did;
    @Column(name = "path")
    private String path;
    @Id
    @Column(name = "keyid")
    private int keyid;
    @Column(name = "price")
    private double price;
    @Column(name = "dlcid")
    private int dlcid;

    public int getDlcid() {
        return dlcid;
    }

    public void setDlcid(int dlcid) {
        this.dlcid = dlcid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setKeyid(int keyid) {
        this.keyid = keyid;
    }

    public int getKeyid() {
        return keyid;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getGid() {
        return gid;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getDid() {
        return did;
    }
}
