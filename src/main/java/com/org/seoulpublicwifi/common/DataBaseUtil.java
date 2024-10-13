package com.org.seoulpublicwifi.common;

import java.sql.*;

public class DataBaseUtil {
    public static Connection connectDB() {

        //SQLite 데이터베이스 파일 위치
        String dbFilePath = "/Users/wany_bunny/Projects/seoul_public_wifi/seoul_public_wifi.db";
        String url = "jdbc:sqlite:" + dbFilePath;

        Connection connection = null;

        try {
            Class.forName("org.sqlite.JDBC");  //JDBC 드라이버 로드
            connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {

        try {
            if (resultSet != null && ! resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (preparedStatement != null && ! preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connection != null && ! connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}