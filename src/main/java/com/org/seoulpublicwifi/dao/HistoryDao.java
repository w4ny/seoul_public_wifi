package com.org.seoulpublicwifi.dao;

import com.org.seoulpublicwifi.common.DataBaseUtil;
import com.org.seoulpublicwifi.dto.HistoryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryDao {
    public static Connection connection;
    public static PreparedStatement preparedStatement;
    public static ResultSet resultSet;

    // 검색 기록을 추가하는 메소드
    public static void searchHistory(String lat, String lnt) {
        connection = null;
        preparedStatement = null;
        resultSet = null;

        try {
            connection = DataBaseUtil.connectDB();

            // 날짜 포맷 설정
            DateFormatSymbols dfs = new DateFormatSymbols(Locale.KOREAN);
            dfs.setWeekdays(new String[]{
                    "unused",
                    "일요일",
                    "월요일",
                    "화요일",
                    "수요일",
                    "목요일",
                    "금요일",
                    "토요일"
            });
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd '('E')' HH:mm:ss", dfs);
            String strDate = sdf.format(new Date());

            String sql = "INSERT INTO history (lat, lnt, search_date) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, lat);
            preparedStatement.setString(2, lnt);
            preparedStatement.setString(3, strDate);

            preparedStatement.executeUpdate();
            System.out.println("데이터가 삽입 완료되었습니다.");

        } catch (SQLException e) {
            System.err.println("검색 기록 삽입 오류: " + e.getMessage());
        } finally {
            DataBaseUtil.close(connection, preparedStatement, resultSet);
        }
    }

    // 검색 기록 목록을 가져오는 메소드
    public List<HistoryDto> searchHistoryList() {
        List<HistoryDto> list = new ArrayList<>();
        connection = null;
        preparedStatement = null;
        resultSet = null;

        try {
            connection = DataBaseUtil.connectDB();
            String sql = "SELECT * FROM history ORDER BY id DESC";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                HistoryDto historyDTO = new HistoryDto(
                        resultSet.getInt("id"),
                        resultSet.getString("lat"),
                        resultSet.getString("lnt"),
                        resultSet.getString("search_date")
                );
                list.add(historyDTO);
            }
        } catch (SQLException e) {
            System.err.println("검색 기록 조회 오류: " + e.getMessage());
        } finally {
            DataBaseUtil.close(connection, preparedStatement, resultSet);
        }

        return list;
    }

    // 검색 기록을 삭제하는 메소드
    public void deleteHistoryList(String id) {
        connection = null;
        preparedStatement = null;
        resultSet = null;

        try {
            // ID가 유효한지 검증
            if (id == null || !id.matches("\\d+")) {
                System.err.println("유효하지 않은 ID입니다: " + id);
                return; // 유효하지 않은 경우 메소드 종료
            }

            connection = DataBaseUtil.connectDB();
            String sql = "DELETE FROM history WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.executeUpdate();
            System.out.println("ID가 " + id + "인 기록이 삭제되었습니다.");

        } catch (SQLException e) {
            System.err.println("검색 기록 삭제 오류: " + e.getMessage());
        } finally {
            DataBaseUtil.close(connection, preparedStatement, resultSet);
        }
    }
}
