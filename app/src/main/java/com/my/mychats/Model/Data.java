package com.my.mychats.Model;

public class Data {

    private String body;
    private String title;
    private String sender;
    private String receiver;

    public Data() {
    }

    public Data(String body, String title, String sender, String receiver) {
        this.body = body;
        this.title = title;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
