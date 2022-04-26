package com.qing.mapper;

import com.qing.entity.myFile;

import java.util.List;

public interface FileMapper {
    public Integer saveFile(myFile myfile);
    public List<myFile> selectFile();
}
