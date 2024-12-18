package io.moblie.platform.feed;

import io.moblie.conf.Conf;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FeedService {
    private static Feed setFeed(ResultSet rs) throws SQLException {
        int feedId = rs.getInt("feed_id");
        String feedDetail = rs.getString("feed_detail");
        Date timestamp = rs.getDate("timestamp");
        String userId = rs.getString("user_id");

        return new Feed(feedId, feedDetail, timestamp, userId);
    }

    public static List<Feed> selectAll() {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;

        List<Feed> feedList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {
            String query = "SELECT * FROM feed";
            psmtQuery = conn.prepareStatement(query);
            rs = psmtQuery.executeQuery();
            while (rs.next()) {
                Feed feed = setFeed(rs);
                feedList.add(feed);
            }
            return feedList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Feed selectById(final int feedId) {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {

            String query = "SELECT * FROM feed WHERE feed_id = ?"; // 물음표는 값을 채우겠다는 의미
            psmtQuery = conn.prepareStatement(query);
            psmtQuery.setInt(1, feedId); // ID 값 설정
            rs = psmtQuery.executeQuery();

            if (rs.next()) {
                Feed feed = setFeed(rs); // 결과를 Customer 객체로 변환
                return feed;
            } else {
                return null; // 결과가 없으면 null 반환
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } finally {
            if (psmtQuery != null) {
                try {
                    psmtQuery.close();
                } catch (SQLException e) {}
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {}
            }
        }
    }

    public static int insert(String feedDetail, Date timestamp, String userId) {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;
        PreparedStatement psmtInsert = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {
            conn.setAutoCommit(false);

            String query = "SELECT MAX(feed_id) FROM feed";
            psmtQuery = conn.prepareStatement(query);
            rs = psmtQuery.executeQuery();

            int newFeedId = 1; // 기본값
            if (rs.next() && rs.getInt(1) > 0) {
                newFeedId = rs.getInt(1) + 1;
            }

            // INSERT 쿼리 작성
            String insertStatement = "INSERT INTO feed (feed_id, feed_detail, timestamp, user_id) VALUES (?, ?, ?, ?)";
            psmtInsert = conn.prepareStatement(insertStatement);
            psmtInsert.setInt(1, newFeedId); // feed_id를 INTEGER 타입으로 설정
            psmtInsert.setString(2, feedDetail);
            psmtInsert.setDate(3, new java.sql.Date(timestamp.getTime()));
            psmtInsert.setString(4, userId);

            if (psmtInsert.executeUpdate() > 0) {
                conn.commit();
                return newFeedId; // 성공 시 새 feed_id 반환
            } else {
                conn.rollback();
                return -1; // 실패 시 -1 반환
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
            if (psmtQuery != null) try { psmtQuery.close(); } catch (SQLException ignored) {}
            if (psmtInsert != null) try { psmtInsert.close(); } catch (SQLException ignored) {}
        }
    }

    public static int update(int feedId, String feedDetail, Date timestamp) {
        ResultSet rs = null;
        PreparedStatement psmtUpdate = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {

            String updateStatement =
                    "UPDATE feed SET feed_detail = ?, timestamp = ? WHERE feed_id = ?";
            psmtUpdate = conn.prepareStatement(updateStatement);
            psmtUpdate.setString(1, feedDetail);
            psmtUpdate.setDate(2, new java.sql.Date(timestamp.getTime()));
            psmtUpdate.setInt(3, feedId);

            return psmtUpdate.executeUpdate(); // 업데이트된 행 수 반환
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (psmtUpdate != null) {
                try {
                    psmtUpdate.close();
                } catch (SQLException e) {}
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {}
            }
        }
    }

    public static int deleteById(int feedId) {
        ResultSet rs = null;
        PreparedStatement psmtDelete = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {

            String deleteStatement = "DELETE FROM feed WHERE feed_id = ?";
            psmtDelete = conn.prepareStatement(deleteStatement);
            psmtDelete.setInt(1, feedId);

            return psmtDelete.executeUpdate(); // 삭제된 행 수 반환
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (psmtDelete != null) {
                try {
                    psmtDelete.close();
                } catch (SQLException e) {}
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {}
            }
        }
    }



}
