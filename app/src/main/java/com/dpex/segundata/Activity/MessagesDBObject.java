package com.dpex.segundata.Activity;

public class MessagesDBObject {
    //private variables
    int _id;
    int _historyid;
    String _title;
    String _body;
    String _timesent;
    String _status;

    // Empty constructor
    public MessagesDBObject(){

    }
    // constructor
    public MessagesDBObject(int id, int historyid, String title, String body, String timesent, String status){
        this._id = id;
        this._historyid =historyid;
        this._title =title;
        this._body =body;
        this._timesent =timesent;
        this._status = status;

    }

    // constructor
    public MessagesDBObject(int historyid, String title, String body, String timesent, String status){

        this._historyid =historyid;
        this._title =title;
        this._body =body;
        this._timesent =timesent;
        this._status = status;
    }

    public int getID(){
        return this._id;
    }

    public void setID(int id){
        this._id = id;
    }



    public int getHistoryID(){
        return this._historyid;
    }

    public void setHistoryID(int historyid){
        this._historyid = historyid;
    }


    public String getTitle(){
        return this._title;
    }

    public void setTitle(String title){
        this._title= title;
    }



    public String getBody(){
        return this._body;
    }

    public void setBody(String body){
        this._body= body;
    }


    public String getTimeSent(){
        return this._timesent;
    }

    public void setTimeSent(String timesent){
        this._timesent =timesent;
    }


    public String getStatus(){
        return this._status;
    }

    public void setStatus(String status){
        this._status = status;
    }



}