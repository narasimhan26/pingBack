package com.example.admin.phone_call;

/**
 * Created by Admin on 2/27/2018.
 */

public class Contact {
    int id;
    String name, number, status, thumbnail;

    public Contact(String name, String number, String status, String thumbnail) {
        this.name = name;
        this.number = number;
        this.status = status;
        this.thumbnail = thumbnail;
    }

    public Contact() {

    }

    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


}

