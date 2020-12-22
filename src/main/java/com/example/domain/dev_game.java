package com.example.domain;

import javax.persistence.*;

@Entity
@Table(name = "dev_game")
public class dev_game {
    @Id
    @Column(name = "keyid")
    private int keyid;
    @Column(name = "uid")
    private int uid;
    @Column(name = "gid")
    private int gid;
    @Column(name = "dlc_num")
    private int dlc_num;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public int getKeyid() {
        return keyid;
    }

    public void setKeyid(int keyid) {
        this.keyid = keyid;
    }

    public int getDlc_num() {
        return dlc_num;
    }

    public void setDlc_num(int dlc_num) {
        this.dlc_num = dlc_num;
    }
}
