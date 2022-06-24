package com.dpex.segundata.Activity;

import android.graphics.drawable.Drawable;

public class MessagesHistoryListPogo {
    private Drawable Image;
    private String name = "";
    private String title = "";
    private String status = "";
    private String body = "";
    private String date= "";
    private int id =0;

    public MessagesHistoryListPogo(int id, Drawable Image, String name, String title, String status, String body, String date) {
        this.Image = Image;
        this.name =name;
        this.status = status;
        this.body= body;
        this.title = title;
        this.date = date;
        this.id = id;


    }


    public Drawable getImage() {
        return Image;
    }

    public void setImage(Drawable image) {
        Image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }
    public void setid(int id) {
        this.id = id;
    }





}
