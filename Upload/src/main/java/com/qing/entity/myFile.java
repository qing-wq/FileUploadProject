package com.qing.entity;

import java.sql.Date;

public class myFile{
    public String fileName;
    public String filePath;
    public Data time;

    public Date getTime() {
        return new Date(new java.util.Date().getTime());
    }

    public void setTime(Data time) {
        this.time = time;
    }

    public myFile() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}