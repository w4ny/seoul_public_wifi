package com.org.seoulpublicwifi.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.org.seoulpublicwifi.common.DataBaseUtil;
import com.org.seoulpublicwifi.dto.WifiDto;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.org.seoulpublicwifi.dao.HistoryDao.searchHistory;

public class WifiDao {

    public static Connection connection;
    public static ResultSet resultSet;
    public static PreparedStatement preparedStatement;

    // 기본 생성자
    public WifiDao() {

    }

    public static int insertPublicWifi(JsonArray jsonArray) {
        int count = 0;

        String sql = "INSERT OR IGNORE INTO seoul_public_wifi " +
                "(x_swifi_mgr_no, x_swifi_wrdofc, x_swifi_main_nm, x_swifi_adres1, x_swifi_adres2, " +
                "x_swifi_instl_floor, x_swifi_instl_ty, x_swifi_instl_mby, x_swifi_svc_se, x_swifi_cmcwr, " +
                "x_swifi_cnstc_year, x_swifi_inout_door, x_swifi_remars3, lat, lnt, work_dttm) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection connection = DataBaseUtil.connectDB();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false); // Auto-Commit 해제

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject data = jsonArray.get(i).getAsJsonObject();

                preparedStatement.setString(1, data.get("X_SWIFI_MGR_NO").getAsString());
                preparedStatement.setString(2, data.get("X_SWIFI_WRDOFC").getAsString());
                preparedStatement.setString(3, data.get("X_SWIFI_MAIN_NM").getAsString());
                preparedStatement.setString(4, data.get("X_SWIFI_ADRES1").getAsString());
                preparedStatement.setString(5, data.get("X_SWIFI_ADRES2").getAsString());
                preparedStatement.setString(6, data.get("X_SWIFI_INSTL_FLOOR").getAsString());
                preparedStatement.setString(7, data.get("X_SWIFI_INSTL_TY").getAsString());
                preparedStatement.setString(8, data.get("X_SWIFI_INSTL_MBY").getAsString());
                preparedStatement.setString(9, data.get("X_SWIFI_SVC_SE").getAsString());
                preparedStatement.setString(10, data.get("X_SWIFI_CMCWR").getAsString());
                preparedStatement.setString(11, data.get("X_SWIFI_CNSTC_YEAR").getAsString());
                preparedStatement.setString(12, data.get("X_SWIFI_INOUT_DOOR").getAsString());
                preparedStatement.setString(13, data.get("X_SWIFI_REMARS3").getAsString());
                preparedStatement.setString(14, data.get("LAT").getAsString());
                preparedStatement.setString(15, data.get("LNT").getAsString());
                preparedStatement.setString(16, data.get("WORK_DTTM").getAsString());

                preparedStatement.addBatch(); // 쿼리 구문을 메모리에 올려둔다.

                // 1000개 기준으로 임시 batch 실행
                if ((i + 1) % 1000 == 0) {
                    count += executeBatchAndCommit(preparedStatement);
                }
            }

            // 남은 배치 실행
            count += executeBatchAndCommit(preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace(); // 적절한 에러 메시지를 로그로 남김
        }

        return count;
    }

    private static int executeBatchAndCommit(PreparedStatement preparedStatement) throws SQLException {
        int[] result = preparedStatement.executeBatch();
        preparedStatement.getConnection().commit(); // 커넥션에 대한 커밋
        return result.length; // 배치한 완료 개수
    }

    public List<WifiDto> getNearestWifiList(String lat, String lnt) {

        connection = null;
        preparedStatement = null;
        resultSet = null;

        List<WifiDto> list = new ArrayList<>();

        try {

            connection = DataBaseUtil.connectDB();

            //위도 경도 구하는 식
            String sql = " SELECT *, " +
                    " round(6371*acos(cos(radians(?))*cos(radians(LAT))*cos(radians(LNT) " +
                    " -radians(?))+sin(radians(?))*sin(radians(LAT))), 4) " +
                    " AS distance " +
                    " FROM seoul_public_wifi " +
                    " ORDER BY distance " +
                    " LIMIT 20;";


            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, Double.parseDouble(lat));
            preparedStatement.setDouble(2, Double.parseDouble(lnt));
            preparedStatement.setDouble(3, Double.parseDouble(lat));

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                WifiDto wifiDTO = WifiDto.builder()
                        .distance(resultSet.getDouble("distance"))
                        .xSwifiMgrNo(resultSet.getString("x_swifi_mgr_no"))
                        .xSwifiWrdofc(resultSet.getString("x_swifi_wrdofc"))
                        .xSwifiMainNm(resultSet.getString("x_swifi_main_nm"))
                        .xSwifiAdres1(resultSet.getString("x_swifi_adres1"))
                        .xSwifiAdres2(resultSet.getString("x_swifi_adres2"))
                        .xSwifiInstlFloor(resultSet.getString("x_swifi_instl_floor"))
                        .xSwifiInstlTy(resultSet.getString("x_swifi_instl_ty"))
                        .xSwifiInstlMby(resultSet.getString("x_swifi_instl_mby"))
                        .xSwifiSvcSe(resultSet.getString("x_swifi_svc_se"))
                        .xSwifiCmcwr(resultSet.getString("x_swifi_cmcwr"))
                        .xSwifiCnstcYear(resultSet.getString("x_swifi_cnstc_year"))
                        .xSwifiInoutDoor(resultSet.getString("x_swifi_inout_door"))
                        .xSwifiRemars3(resultSet.getString("x_swifi_remars3"))
                        .lat(resultSet.getString("lat"))
                        .lnt(resultSet.getString("lnt"))
                        .workDttm(String.valueOf(resultSet.getTimestamp("work_dttm").toLocalDateTime()))
                        .build();

                list.add(wifiDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseUtil.close(connection, preparedStatement, resultSet);
        }
        searchHistory(lat, lnt);        

        return list;
    }

    public List<WifiDto> selectWifiList(String mgrNo, double distance) {

        connection = null;
        preparedStatement = null;
        resultSet = null;

        List<WifiDto> list = new ArrayList<>();

        try {
            connection = DataBaseUtil.connectDB();
            String sql = " select * from seoul_public_wifi where x_swifi_mgr_no = ? ";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, mgrNo);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                WifiDto wifiDTO = WifiDto.builder()
                        .distance(distance)
                        .xSwifiMgrNo(resultSet.getString("X_SWIFI_MGR_NO"))
                        .xSwifiWrdofc(resultSet.getString("X_SWIFI_WRDOFC"))
                        .xSwifiMainNm(resultSet.getString("X_SWIFI_MAIN_NM"))
                        .xSwifiAdres1(resultSet.getString("X_SWIFI_ADRES1"))
                        .xSwifiAdres2(resultSet.getString("X_SWIFI_ADRES2"))
                        .xSwifiInstlFloor(resultSet.getString("X_SWIFI_INSTL_FLOOR"))
                        .xSwifiInstlTy(resultSet.getString("X_SWIFI_INSTL_TY"))
                        .xSwifiInstlMby(resultSet.getString("X_SWIFI_INSTL_MBY"))
                        .xSwifiSvcSe(resultSet.getString("X_SWIFI_SVC_SE"))
                        .xSwifiCmcwr(resultSet.getString("X_SWIFI_CMCWR"))
                        .xSwifiCnstcYear(resultSet.getString("X_SWIFI_CNSTC_YEAR"))
                        .xSwifiInoutDoor(resultSet.getString("X_SWIFI_INOUT_DOOR"))
                        .xSwifiRemars3(resultSet.getString("X_SWIFI_REMARS3"))
                        .lat(resultSet.getString("LAT"))
                        .lnt(resultSet.getString("LNT"))
                        .workDttm(String.valueOf(resultSet.getTimestamp("work_dttm").toLocalDateTime()))
                        .build();
                list.add(wifiDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseUtil.close(connection, preparedStatement, resultSet);
        }

        return list;
    }

    public WifiDto selectWifi(String mgrNo) {
        WifiDto wifiDTO = new WifiDto();

        connection = null;
        preparedStatement = null;
        resultSet = null;

        try {
            connection = DataBaseUtil.connectDB();
            String sql = " select * from seoul_public_wifi where x_swifi_mgr_no = ? ";
            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, mgrNo);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                wifiDTO.setXSwifiMgrNo(resultSet.getString("X_SWIFI_MGR_NO"));
                wifiDTO.setXSwifiWrdofc(resultSet.getString("X_SWIFI_WRDOFC"));
                wifiDTO.setXSwifiMainNm(resultSet.getString("X_SWIFI_MAIN_NM"));
                wifiDTO.setXSwifiAdres1(resultSet.getString("X_SWIFI_ADRES1"));
                wifiDTO.setXSwifiAdres2(resultSet.getString("X_SWIFI_ADRES2"));
                wifiDTO.setXSwifiInstlFloor(resultSet.getString("X_SWIFI_INSTL_FLOOR"));
                wifiDTO.setXSwifiInstlTy(resultSet.getString("X_SWIFI_INSTL_TY"));
                wifiDTO.setXSwifiInstlMby(resultSet.getString("X_SWIFI_INSTL_MBY"));
                wifiDTO.setXSwifiSvcSe(resultSet.getString("X_SWIFI_SVC_SE"));
                wifiDTO.setXSwifiCmcwr(resultSet.getString("X_SWIFI_CMCWR"));
                wifiDTO.setXSwifiCnstcYear(resultSet.getString("X_SWIFI_CNSTC_YEAR"));
                wifiDTO.setXSwifiInoutDoor(resultSet.getString("X_SWIFI_INOUT_DOOR"));
                wifiDTO.setXSwifiRemars3(resultSet.getString("X_SWIFI_REMARS3"));
                wifiDTO.setLat(resultSet.getString("LAT"));
                wifiDTO.setLnt(resultSet.getString("LNT"));
                wifiDTO.setWorkDttm(String.valueOf(resultSet.getTimestamp("work_dttm").toLocalDateTime()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DataBaseUtil.close(connection, preparedStatement, resultSet);
        }

        return wifiDTO;
    }
}
