package com.example.domain;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "postbar")
public class postbar {
    @Id
    @Column(name = "tid")
    private int tid;
    @Column(name = "gid")
    private int gid;
    @Column(name = "uid")
    private int uid;
    @Column(name = "create_time")
    private Date create_time;
    @Column(name = "points")
    private float points;
    @Column(name = "content")
    private String content;
    @Column(name = "zan")
    private int zan;
    @Column(name = "cai")
    private int cai;
    @Column(name = "picture")
    private String picture;
    @Column(name = "isvideo")
    private int isvideo;

    public int getIsvideo() {
        return isvideo;
    }

    public void setIsvideo(int isvideo) {
        this.isvideo = isvideo;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getTid() {
        return tid;
    }


    public void setPoints(float points) {
        this.points = points;
    }

    public float getPoints() {
        return points;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getGid() {
        return gid;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCai() {
        return cai;
    }

    public int getZan() {
        return zan;
    }

    public void setCai(int cai) {
        this.cai = cai;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
