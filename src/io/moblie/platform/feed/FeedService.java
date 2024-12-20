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
            e.printStackTrace();
            return null;
        } finally {
            if (psmtQuery != null) {
                try {
                    psmtQuery.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Feed selectById(final int feedId) {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {
            String query = "SELECT * FROM feed WHERE feed_id = ?";

            psmtQuery = conn.prepareStatement(query);
            psmtQuery.setInt(1, feedId);
            rs = psmtQuery.executeQuery();

            if (rs.next()) {
                Feed feed = setFeed(rs);
                return feed;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (psmtQuery != null) {
                try {
                    psmtQuery.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

            int newFeedId = 1;
            if (rs.next() && rs.getInt(1) > 0) {
                newFeedId = rs.getInt(1) + 1;
            }

            String insertStatement = "INSERT INTO feed (feed_id, feed_detail, timestamp, user_id) VALUES (?, ?, ?, ?)";
            psmtInsert = conn.prepareStatement(insertStatement);
            psmtInsert.setInt(1, newFeedId);
            psmtInsert.setString(2, feedDetail);
            psmtInsert.setDate(3, new java.sql.Date(timestamp.getTime()));
            psmtInsert.setString(4, userId);

            if (psmtInsert.executeUpdate() > 0) {
                conn.commit();
                return newFeedId;
            } else {
                conn.rollback();
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (psmtQuery != null) {
                try {
                    psmtQuery.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psmtInsert != null) {
                try {
                    psmtInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int update(int feedId, String feedDetail, Date timestamp) {
        PreparedStatement psmtUpdate = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {
            String updateStatement = "UPDATE feed SET feed_detail = ?, timestamp = ? WHERE feed_id = ?";
            psmtUpdate = conn.prepareStatement(updateStatement);
            psmtUpdate.setString(1, feedDetail);
            psmtUpdate.setDate(2, new java.sql.Date(timestamp.getTime()));
            psmtUpdate.setInt(3, feedId);

            return psmtUpdate.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (psmtUpdate != null) {
                try {
                    psmtUpdate.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int deleteById(int feedId) {
        PreparedStatement psmtDeleteComments = null;
        PreparedStatement psmtDeleteFeed = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {
            conn.setAutoCommit(false);

            String deleteCommentsQuery = "DELETE FROM comment WHERE feed_id = ?";
            psmtDeleteComments = conn.prepareStatement(deleteCommentsQuery);
            psmtDeleteComments.setInt(1, feedId);
            psmtDeleteComments.executeUpdate();

            String deleteFeedQuery = "DELETE FROM feed WHERE feed_id = ?";
            psmtDeleteFeed = conn.prepareStatement(deleteFeedQuery);
            psmtDeleteFeed.setInt(1, feedId);

            int rowsAffected = psmtDeleteFeed.executeUpdate();
            conn.commit();
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (psmtDeleteComments != null) {
                try {
                    psmtDeleteComments.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psmtDeleteFeed != null) {
                try {
                    psmtDeleteFeed.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
