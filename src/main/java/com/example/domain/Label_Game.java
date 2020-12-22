package com.example.domain;

import jdk.jfr.Relational;

import javax.persistence.*;

@Entity
@Table(name = "label_game")
public class Label_Game {
    @Column(name = "Label")
    private String label;
    @Column(name = "gid")
    private int id;
    @Id
    @Column(name = "keyid")
    private int keyid;

    public Label_Game() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getKeyid() {
        return keyid;
    }

    public void setKeyid(int keyid) {
        this.keyid = keyid;
    }
}
