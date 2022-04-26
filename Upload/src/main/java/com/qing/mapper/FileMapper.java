package com.qing.mapper;

import com.qing.entity.MyFile;

import java.util.List;

public interface FileMapper {
    public Integer saveFile(MyFile myfile);
    public List<MyFile> selectFile();
}
