package com.example.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @Column(name = "gid")
    private Long gid;
    @Column(name = "name")
    private String name;
    @Column(name = "Description")
    private String Description;
    @Column(name = "price")
    private double price;
    @Column(name = "path")
    private String path;
    @Column(name = "discount")
    private double discount;
    @Column(name = "begin_time")
    private Date begin_time;
    @Column(name = "end_time")
    private Date end_time;
    @Column(name = "release_date")
    private Date release_date;
    @Column(name = "uid")
    private int uid;
    @Column(name = "g_des")
    private String g_des;

    public Game() {
        super();
    }

    public Game(String Name) {
        super();
        this.name = Name;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getG_des() {
        return g_des;
    }

    public void setG_des(String g_des) {
        this.g_des = g_des;
    }

    public int getUid() {
        return uid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public Long getGid() {
        return gid;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public Long getId() {
        return gid;
    }

    public void setId(Long gid) {
        this.gid = gid;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }

    public Date getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(Date begin_time) {
        this.begin_time = begin_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }
}

