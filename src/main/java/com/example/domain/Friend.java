package com.example.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@IdClass(FriendKey.class)
@Table(name = "friend")
public class Friend implements Serializable {
    @Id
    @Column(name = "User_1")
    private int user1;
    @Id
    @Column(name = "User_2")
    private int user2;
    @Column(name = "add_date")
    private Timestamp addDate;
    @Column(name = "intimacy")
    private int intimacy;
    @Column(name = "relationship")
    private int relationship;

    public Friend(){super();}

    public Friend(int user1, int user2, Timestamp addDate, int intimacy, int relationship) {
        this.user1 = user1;
        this.user2 = user2;
        this.addDate = addDate;
        this.intimacy = intimacy;
        this.relationship = relationship;
    }

    public int getUser1() {
        return user1;
    }

    public void setUser1(int user1) {
        this.user1 = user1;
    }

    public int getUser2() {
        return user2;
    }

    public void setUser2(int user2) {
        this.user2 = user2;
    }

    public Timestamp getAddDate() {
        return addDate;
    }

    public void setAddDate(Timestamp addDate) {
        this.addDate = addDate;
    }

    public int getIntimacy() {
        return intimacy;
    }

    public void setIntimacy(int intimacy) {
        this.intimacy = intimacy;
    }

    public int getRelationship() {
        return relationship;
    }

    public void setRelationship(int relationship) {
        this.relationship = relationship;
    }
}
