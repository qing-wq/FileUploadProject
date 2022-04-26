package com.qing.mapper;

import com.qing.entity.Data;
import com.qing.entity.myFile;
import com.qing.utils.jdbcUtil;

import java.nio.channels.Pipe;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public Data data;
    public Connection connection = null;
    public PreparedStatement preparedStatement = null;
    public ResultSet resultSet = null;

    public UserDao(Data data) {
        this.data = data;
    }
    // 插入上传的文件
    public List<myFile> save() throws SQLException {
        List<myFile> list = new ArrayList<>();
        connection = jdbcUtil.getConnection();
        String sql = "insert into `file`(fileName,`time`,filePath) values(?,?,?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,data.getFileName());
        preparedStatement.setDate(2,new Date(new java.util.Date().getTime()));
        preparedStatement.setString(3,data.getFilePath());
        int i = preparedStatement.executeUpdate();
        System.out.println("受影响的行数为："+i);

        // 返回数据
        String sql2 = "select * from file";
        preparedStatement = connection.prepareStatement(sql2);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            myFile myFile = new myFile();
            myFile.fileName = resultSet.getString("fileName");
            myFile.filePath = resultSet.getString("filePath");
            list.add(myFile);
        }
        jdbcUtil.release(connection, this.preparedStatement, this.resultSet);
        return list;
    }
}
