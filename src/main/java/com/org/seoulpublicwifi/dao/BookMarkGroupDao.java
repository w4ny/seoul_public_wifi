package com.org.seoulpublicwifi.dao;

import com.org.seoulpublicwifi.common.DataBaseUtil;
import com.org.seoulpublicwifi.dto.BookMarkGroupDto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookMarkGroupDao {
    public int groupCount() {
        int count = 0;

        String sql = "SELECT COUNT(*) FROM bookmark_group";
        try (Connection connection = DataBaseUtil.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error counting bookmark groups: " + e.getMessage());
        }

        return count;
    }

    public int saveBookMarkGroup(BookMarkGroupDto bookMarkGroupDTO) {
        int affected = 0;

        String sql = "INSERT INTO bookmark_group(name, order_no, register_date) VALUES (?, ?, ?)";
        try (Connection connection = DataBaseUtil.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, bookMarkGroupDTO.getName());
            preparedStatement.setInt(2, bookMarkGroupDTO.getOrder());
            preparedStatement.setString(3, bookMarkGroupDTO.getRegister_date());

            affected = preparedStatement.executeUpdate();
            System.out.println(affected > 0 ? "북마크 그룹 데이터 삽입 완료" : "북마크 그룹 데이터 삽입 실패");

        } catch (SQLException e) {
            System.err.println("Error inserting bookmark group: " + e.getMessage());
        }

        return affected;
    }

    public BookMarkGroupDto selectBookMarkGroup(int id) {
        BookMarkGroupDto bookMarkGroupDTO = null;

        String sql = "SELECT * FROM bookmark_group WHERE id = ?";
        try (Connection connection = DataBaseUtil.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    bookMarkGroupDTO = BookMarkGroupDto.builder()
                            .id(resultSet.getInt("id"))
                            .name(resultSet.getString("name"))
                            .order(resultSet.getInt("order_no"))
                            .register_date(resultSet.getString("register_date"))
                            .update_date(resultSet.getString("update_date"))
                            .build();
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving bookmark group: " + e.getMessage());
        }

        return bookMarkGroupDTO;
    }

    public List<BookMarkGroupDto> getAllBookMarkGroups() {
        List<BookMarkGroupDto> list = new ArrayList<>();

        String sql = "SELECT * FROM bookmark_group ORDER BY id";
        try (Connection connection = DataBaseUtil.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                BookMarkGroupDto bookMarkGroupDTO = BookMarkGroupDto.builder()
                        .id(resultSet.getInt("id"))
                        .name(resultSet.getString("name"))
                        .order(resultSet.getInt("order_no"))
                        .register_date(resultSet.getString("register_date"))
                        .update_date(resultSet.getString("update_date"))
                        .build();

                list.add(bookMarkGroupDTO);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving bookmark groups: " + e.getMessage());
        }

        return list;
    }

    public int updateBookMarkGroup(int id, String name, int order) {
        int result = 0;

        String sql = "UPDATE bookmark_group SET name = ?, order_no = ?, update_date = ? WHERE id = ?";
        try (Connection connection = DataBaseUtil.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, order);
            preparedStatement.setString(3, String.valueOf(Timestamp.valueOf(LocalDateTime.now())));
            preparedStatement.setInt(4, id);

            result = preparedStatement.executeUpdate();
            System.out.println(result > 0 ? "북마크 그룹 데이터 업데이트 완료" : "북마크 그룹 데이터 업데이트 실패");

        } catch (SQLException e) {
            System.err.println("Error updating bookmark group: " + e.getMessage());
        }

        return result;
    }

    public int deleteBookMarkGroup(int id) {
        int affected = 0;

        String sql = "DELETE FROM bookmark_group WHERE id = ?";
        try (Connection connection = DataBaseUtil.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            affected = preparedStatement.executeUpdate();
            System.out.println(affected > 0 ? "북마크 그룹 데이터 삭제 완료" : "북마크 그룹 데이터 삭제 실패");

        } catch (SQLException e) {
            System.err.println("Error deleting bookmark group: " + e.getMessage());
        }

        return affected;
    }
}
