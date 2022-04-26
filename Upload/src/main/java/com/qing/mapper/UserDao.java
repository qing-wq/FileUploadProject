package com.qing.mapper;

import com.qing.entity.MyFile;
import com.qing.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public MyFile myFile;
    public UserDao(MyFile myFile) {
        this.myFile = myFile;
    }
   // 插入上传的文件
    public List<MyFile> save() throws SQLException {
        List<MyFile> list = new ArrayList<>();
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        FileMapper mapper = sqlSession.getMapper(FileMapper.class);
        Integer n = mapper.saveFile(myFile);
        System.out.println("受影响的行数为：" + n);
        if (n <= 0) {
            System.out.println("插入数据失败，请重试");
            sqlSession.close();
            return null;
        }
        // 返回数据
        return mapper.selectFile();
    }
}
