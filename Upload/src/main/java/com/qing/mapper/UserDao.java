package com.qing.mapper;

import com.qing.entity.MyFile;
import com.qing.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public MyFile myFile;
//    public Connection connection = null;
//    public PreparedStatement preparedStatement = null;
//    public ResultSet resultSet = null;
//
    public UserDao(MyFile myFile) {
        this.myFile = myFile;
    }
//    // 插入上传的文件
    public List<MyFile> save() throws SQLException {
        List<MyFile> list = new ArrayList<>();
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        FileMapper mapper = sqlSession.getMapper(FileMapper.class);
        Integer n = mapper.saveFile(myFile);
        System.out.println("受影响的行数为："+ n);
        if (n < 0) {
            System.out.println("插入数据失败，请重试");
        }
//        connection = jdbcUtil.getConnection();
//        String sql = "insert into `file`(fileName,`time`,filePath) values(?,?,?)";
//        preparedStatement = connection.prepareStatement(sql);
//        preparedStatement.setString(1,data.getFileName());
//        preparedStatement.setDate(2,new Date(new java.util.Date().getTime()));
//        preparedStatement.setString(3,data.getFilePath());
//        int i = preparedStatement.executeUpdate();
//        System.out.println("受影响的行数为："+i);
//
//        // 返回数据
//        String sql2 = "select * from file";
//        preparedStatement = connection.prepareStatement(sql2);
//        resultSet = preparedStatement.executeQuery();
//        while (resultSet.next()) {
//            myFile myFile = new myFile();
//            myFile.fileName = resultSet.getString("fileName");
//            myFile.filePath = resultSet.getString("filePath");
//            list.add(myFile);
//        }
//        jdbcUtil.release(connection, this.preparedStatement, this.resultSet);
//        return list;
//    }


}
