package io.moblie.platform.friend;

import io.moblie.conf.Conf;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendService {
    private static Friend setFriend(ResultSet rs) throws SQLException {
        String userId = rs.getString("user_id");
        String friendId = rs.getString("friend_id");

        return new Friend(userId, friendId);
    }

    public static List<Friend> selectAll() {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;

        List<Friend> friendList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {

            String query = "SELECT * FROM friend";

            psmtQuery = conn.prepareStatement(query);

            rs = psmtQuery.executeQuery();

            while (rs.next()) {
                Friend friend = setFriend(rs);
                friendList.add(friend);
            }
            return friendList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } finally {
            if (psmtQuery != null) {
                try {
                    psmtQuery.close();
                } catch (SQLException ignored) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static List<Friend> selectById(final String userId) {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;

        List<Friend> friendList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {

            String query = "SELECT * FROM friend WHERE user_id = ? ";

            psmtQuery = conn.prepareStatement(query);
            psmtQuery.setString(1, userId);

            rs = psmtQuery.executeQuery();

            while (rs.next()) {
                Friend friendship = setFriend(rs);
                friendList.add(friendship);
            }
            return friendList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } finally {
            if (psmtQuery != null) {
                try {
                    psmtQuery.close();
                } catch (SQLException e) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static Friend selectFriendshipById(final String userId, final String friendId) {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {
            String query = "SELECT * FROM friend WHERE user_id = ? AND friend_id = ?";

            psmtQuery = conn.prepareStatement(query);
            psmtQuery.setString(1, userId);
            psmtQuery.setString(2, friendId);

            rs = psmtQuery.executeQuery();

            if (rs.next()) {
                Friend friendship = setFriend(rs);
                return friendship;
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (psmtQuery != null) {
                try {
                    psmtQuery.close();
                } catch (SQLException e) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }

            }
        }
    }

    public static int insert(final String userId, String friendId) {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;
        PreparedStatement psmtUpdate = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {

            String query = "SELECT * FROM friend WHERE user_id = ? AND friend_id = ?";

            psmtQuery = conn.prepareStatement(query);
            psmtQuery.setString(1, userId);
            psmtQuery.setString(2, friendId);

            rs = psmtQuery.executeQuery();

            if (!rs.next()) {
                String insertStatement =
                        "INSERT INTO friend (user_id, friend_id)" +
                                "VALUES(?,?)";
                psmtUpdate = conn.prepareStatement(insertStatement);
                // 물음표의 위치 작성
                psmtUpdate.setString(1, userId);
                psmtUpdate.setString(2, friendId);

                return psmtUpdate.executeUpdate();
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;

        } finally {
            if (psmtQuery != null) {
                try {
                    psmtQuery.close();
                } catch (SQLException e) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static int deleteById(String userId, String friendId) {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;
        PreparedStatement psmtUpdate = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {

            String query = "SELECT * FROM friend WHERE user_id = ? AND friend_id = ?";

            psmtQuery = conn.prepareStatement(query);
            psmtQuery.setString(1, userId);
            psmtQuery.setString(2, friendId);
            rs = psmtQuery.executeQuery();

            if (rs.next()) {
                String updateStatement =
                        "DELETE FROM friend WHERE user_id = ? AND friend_id = ?";

                psmtUpdate = conn.prepareStatement(updateStatement);
                psmtUpdate.setString(1, userId);
                psmtUpdate.setString(2, friendId);
                return psmtUpdate.executeUpdate();
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;

        } finally {
            if (psmtQuery != null) {
                try {
                    psmtQuery.close();
                } catch (SQLException e) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
        }

    }
}
