package com.example.domain;

public class ChatInfo {
    private String userName;
    private String sendTime;
    private String imgSrc;
    private String content;

    public ChatInfo(String userName, String sendTime, String imgSrc, String content) {
        this.userName = userName;
        this.sendTime = sendTime;
        this.imgSrc = imgSrc;
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
