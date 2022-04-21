package com.qing.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Data {
    public String fileName;
    public String username;
    public Date date;

    public Data() {
    }

    public Data(String fileName, String username, Date date) {
        this.fileName = fileName;
        this.username = username;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
