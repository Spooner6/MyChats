package com.my.mychats.Model;

public class Chat {
    private String sender;
    private String receiver;
    private String message;
    private String imageChatUrl;
    private String type;


    public Chat(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public Chat(String sender, String receiver, String message, String imageChatUrl, String type) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.imageChatUrl = imageChatUrl;
        this.type = type;
    }


    public Chat() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageChatUrl() {
        return imageChatUrl;
    }

    public void setImageChatUrl(String imageChatUrl) {
        this.imageChatUrl = imageChatUrl;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String text) {
        this.message = text;
    }
}
