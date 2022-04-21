package com.qing.mapper;

import com.qing.entity.Data;
import com.qing.utils.jdbcUtil;

import java.nio.channels.Pipe;
import java.sql.*;

public class UserDao {

    public Data data;
    public Connection connection = null;
    public PreparedStatement preparedStatement = null;
    public ResultSet resultSet = null;

    public UserDao(Data data) {
        this.data = data;
    }

    public void save() throws SQLException {
        String username = data.username;
        String fileName = data.fileName;

        connection = jdbcUtil.getConnection();
        String sql = "insert into `bank`(username,fileName,`time`) values(?,?,?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,fileName);
        preparedStatement.setDate(3,new Date(new java.util.Date().getTime()));
        int i = preparedStatement.executeUpdate();
        System.out.println("受影响的行数为："+i);
        jdbcUtil.release(connection,preparedStatement,resultSet);
    }
}
