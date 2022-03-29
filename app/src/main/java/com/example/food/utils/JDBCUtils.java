package com.example.food.utils;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {

    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("驱动加载成功");
        }catch (ClassNotFoundException e){
            e.printStackTrace();

        }
    }

    public static Connection getConn(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://192.168.43.49:3306/test?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&testOnBorrow=true&validationQuery=select 1","root","123456");
            System.out.println("数据库连接成功");
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn){
        try{
            conn.close();
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
    }


}
