package com.somelogs.load.context_cloassloader;

import lombok.Data;

import java.sql.*;
import java.util.Properties;

/**
 * 线程上下文类加载器
 * @author LBG - 2017/10/19 0019
 */
public class SimpleJdbc {

    private static final String URL = "jdbc:mysql://localhost:3306/data_test?useUnicode=true&amp;characterEncoding=utf-8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        Properties prop = new Properties();
        prop.put("user", USERNAME);
        prop.put("password", PASSWORD);
        Connection conn = new com.mysql.jdbc.Driver().connect(URL, prop);
        //Connection conn = getConnection();
        if (conn != null) {
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM user LIMIT 10");
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setAge(resultSet.getInt("age"));
                user.setUname(resultSet.getString("uname"));
                user.setRegtime(resultSet.getString("regtime"));
                System.out.println(user);
            }
        }
    }

    @Data
    static class User {
        private Integer id;
        private String uname;
        private String regtime;
        private Integer age;
    }

}
