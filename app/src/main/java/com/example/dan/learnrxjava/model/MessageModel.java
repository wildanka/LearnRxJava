package com.example.dan.learnrxjava.model;

/**
 * Created by DAN on 8/11/2018.
 */

public class MessageModel {
    private int id;
    private String title;
    private String body;
    private int userID;


    public MessageModel(int id, String title, String body, int userID) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
