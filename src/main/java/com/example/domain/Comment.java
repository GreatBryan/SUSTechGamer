package com.example.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="comment")
public class Comment implements Serializable {
    @Id
    @Column(name = "cid")
    private int cid;

    @Column(name = "uid")
    private int user;

    @Column(name = "tid")
    private int tiezi;

    @Column(name = "pid")
    private int parent;

    @Column(name = "release_time")
    private Timestamp time;

    @Column(name = "content")
    private String content;

    @Column(name = "depth")
    private int depth;

    @Column(name = "display")
    private int display;

    @Column(name = "like_num")
    private int like_num;

    @Column(name = "dislike_num")
    private int dislike;

    @Column(name = "reid")
    private int reid;

    @Column(name = "isvideo")
    private int isvideo;

    public Comment(){super();}

    public Comment(int cid, int user, int tiezi, int parent,
                   Timestamp time, String content, int depth,
                   int display, int like_num, int dislike) {
        this.cid = cid;
        this.user = user;
        this.tiezi = tiezi;
        this.parent = parent;
        this.time = time;
        this.content = content;
        this.depth = depth;
        this.display = display;
        this.like_num = like_num;
        this.dislike = dislike;
    }

    public int getReid() {
        return reid;
    }

    public void setReid(int reid) {
        this.reid = reid;
    }

    public void setIsvideo(int isvideo) {
        this.isvideo = isvideo;
    }

    public int getIsvideo() {
        return isvideo;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getTiezi() {
        return tiezi;
    }

    public void setTiezi(int tiezi) {
        this.tiezi = tiezi;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public int getDislike() {
        return dislike;
    }

    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    @Override
    public String toString(){
        return "cid: "+cid
                +"uid: "+user
                +"tid: "+tiezi
                +"pid: "+parent
                +"depth: "+depth
                +"release time: "+time
                +"content: "+content
                +"display: "+display
                +"like: "+like_num
                +"dislike: "+dislike;
    }
}
