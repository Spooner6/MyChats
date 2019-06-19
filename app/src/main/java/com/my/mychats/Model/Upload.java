package com.my.mychats.Model;

public class Upload {
    String imgUrl;
    String receiver;
    String sender;

    public Upload() {
    }

    public Upload(String imgUrl, String receiver, String sender) {
        this.imgUrl = imgUrl;
        this.receiver = receiver;
        this.sender = sender;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Upload(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
