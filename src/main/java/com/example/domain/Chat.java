package com.example.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "chat")
public class Chat {
    @Id
    @Column(name = "chatid")
    private int chatId;

    @Column(name = "sender")
    private int sender;

    @Column(name = "recv")
    private int recv;

    @Column(name = "content")
    private String content;

    @Column(name = "send_date")
    private Date sendDate;

    public Chat(){}

    public Chat(int sender, int recv, String content, Date sendDate) {
        this.sender = sender;
        this.recv = recv;
        this.content = content;
        this.sendDate = sendDate;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getRecv() {
        return recv;
    }

    public void setRecv(int recv) {
        this.recv = recv;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
}
