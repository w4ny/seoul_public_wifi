package com.org.seoulpublicwifi.dao;

import com.org.seoulpublicwifi.common.DataBaseUtil;
import com.org.seoulpublicwifi.dto.BookMarkDto;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookMarkDao {
    public BookMarkDao() {}

    public int count() {
        int count = 0;

        String sql = "SELECT COUNT(*) FROM bookmark_list";
        try (Connection connection = DataBaseUtil.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error counting bookmarks: " + e.getMessage());
        }

        return count;
    }

    public int insertBookMark(BookMarkDto dto) {
        int affected = 0;

        String sql = "INSERT OR REPLACE INTO bookmark_list (group_id, mgr_no, register_date) VALUES (?, ?, ?)";
        try (Connection connection = DataBaseUtil.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, dto.getGroupId());
            preparedStatement.setString(2, dto.getMgrNo());
            preparedStatement.setString(3, String.valueOf(new Timestamp(System.currentTimeMillis())));

            affected = preparedStatement.executeUpdate();
            System.out.println(affected > 0 ? "북마크 데이터 삽입 완료" : "북마크 데이터 삽입 실패");

        } catch (SQLException e) {
            System.err.println("Error inserting bookmark: " + e.getMessage());
        }

        return affected;
    }

    public int deleteBookMark(int id) {
        int affected = 0;

        String sql = "DELETE FROM bookmark_list WHERE id = ?";
        try (Connection connection = DataBaseUtil.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            affected = preparedStatement.executeUpdate();
            System.out.println(affected > 0 ? "북마크 데이터 삭제 완료" : "북마크 데이터 삭제 실패");

        } catch (SQLException e) {
            System.err.println("Error deleting bookmark: " + e.getMessage());
        }

        return affected;
    }

    public BookMarkDto selectBookMarkList(int id) {
        BookMarkDto bookMarkDTO = null;

        String sql = "SELECT * FROM bookmark_list WHERE id = ?";
        try (Connection connection = DataBaseUtil.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    bookMarkDTO = new BookMarkDto();
                    bookMarkDTO.setId(resultSet.getInt("id"));
                    bookMarkDTO.setGroupId(resultSet.getInt("group_id"));
                    bookMarkDTO.setMgrNo(resultSet.getString("mgr_no"));
                    bookMarkDTO.setRegister_date(resultSet.getString("register_date"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving bookmark: " + e.getMessage());
        }

        return bookMarkDTO;
    }

    public List<BookMarkDto> getAllBookmarks() {
        List<BookMarkDto> list = new ArrayList<>();

        String sql = "SELECT bookmark_list.* FROM bookmark_list INNER JOIN bookmark_group ON bookmark_list.group_id = bookmark_group.id ORDER BY bookmark_group.order_no";
        try (Connection connection = DataBaseUtil.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                BookMarkDto bookMarkDTO = BookMarkDto.builder()
                        .id(resultSet.getInt("id"))
                        .groupId(resultSet.getInt("group_id"))
                        .mgrNo(resultSet.getString("mgr_no"))
                        .register_date(resultSet.getString("register_date"))
                        .build();

                list.add(bookMarkDTO);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving bookmarks: " + e.getMessage());
        }

        return list;
    }
}
