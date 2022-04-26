package com.qing.entity;

import java.util.Date;

public class MyFile {
    public String fileName;
    public String username;
    public java.sql.Date time;
    public String filePath;


    public MyFile() {
    }

    public MyFile(String fileName, String username, Date date) {
        this.fileName = fileName;
        this.username = username;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public java.sql.Date getTime() {
        return new java.sql.Date(new java.util.Date().getTime());
    }

    public void setTime(java.sql.Date time) {
        this.time = time;
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
