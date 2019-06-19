package com.my.mychats.Model;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String username;
    private String imageURL;
    private String name;
    private String lastname;

    public User(String id, String username, String imageURL, String jmeno, String prijmeni) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.name = jmeno;
        this.lastname = prijmeni;
    }

    public User() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public User(String id, String username, String imageURL) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
