package com.example.planthome.TutorialManagement.Model;

public class Tutes {
    private String name,type,image,tid,date,time,met;

    public  Tutes()
    {

    }

    public Tutes(String name, String type, String image, String tid, String date, String time, String met) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.tid = tid;
        this.date = date;
        this.time = time;
        this.met = met;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMet() {
        return met;
    }

    public void setMet(String met) {
        this.met = met;
    }
}
