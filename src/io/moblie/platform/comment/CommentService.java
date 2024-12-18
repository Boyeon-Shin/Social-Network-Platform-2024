package io.moblie.platform.comment;

import io.moblie.conf.Conf;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentService {

    private static Comment setComment(ResultSet rs) throws SQLException {
        int commentId = rs.getInt("comment_id");
        String commentDetail = rs.getString("comment_detail");
        Date timestamp = rs.getDate("timestamp");
        String userId = rs.getString("user_id");

        return new Comment(commentId, commentDetail, timestamp, userId);
    }

    public static List<Comment> selectAll() {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;

        List<Comment> commentList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {
            String query = "SELECT * FROM comment";
            psmtQuery = conn.prepareStatement(query);
            rs = psmtQuery.executeQuery();
            while (rs.next()) {
                Comment comment = setComment(rs);
                commentList.add(comment);
            }
            return commentList;
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

    public static Comment selectById(int commentId) {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {
            String query = "SELECT * FROM comment WHERE comment_id = ?";
            psmtQuery = conn.prepareStatement(query);
            psmtQuery.setInt(1, commentId);
            rs = psmtQuery.executeQuery();

            if (rs.next()) {
                return setComment(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psmtQuery != null) {
                try {
                    psmtQuery.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int insert(String commentDetail, Date timestamp, String userId, int feedId) {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;
        PreparedStatement psmtInsert = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {
            conn.setAutoCommit(false);

            String query = "SELECT MAX(comment_id) FROM comment";
            psmtQuery = conn.prepareStatement(query);
            rs = psmtQuery.executeQuery();

            int newCommentId = 1;
            if (rs.next() && rs.getInt(1) > 0) {
                newCommentId = rs.getInt(1) + 1;
            }

            String insertStatement = "INSERT INTO comment (comment_id, comment_detail, timestamp, user_id, feed_id) VALUES (?, ?, ?, ?, ?)";
            psmtInsert = conn.prepareStatement(insertStatement);
            psmtInsert.setInt(1, newCommentId);
            psmtInsert.setString(2, commentDetail);
            psmtInsert.setDate(3, new java.sql.Date(timestamp.getTime()));
            psmtInsert.setString(4, userId);
            psmtInsert.setInt(5, feedId);

            if (psmtInsert.executeUpdate() > 0) {
                conn.commit();
                return newCommentId;
            } else {
                conn.rollback();
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
        }
    }

    public static int update(int commentId, String commentDetail, Date timestamp) {
        PreparedStatement psmtUpdate = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {
            String updateStatement = "UPDATE comment SET comment_detail = ?, timestamp = ? WHERE comment_id = ?";
            psmtUpdate = conn.prepareStatement(updateStatement);
            psmtUpdate.setString(1, commentDetail);
            psmtUpdate.setDate(2, new java.sql.Date(timestamp.getTime()));
            psmtUpdate.setInt(3, commentId);

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

    public static int deleteById(int commentId) {
        PreparedStatement psmtDelete = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {
            String deleteStatement = "DELETE FROM comment WHERE comment_id = ?";
            psmtDelete = conn.prepareStatement(deleteStatement);
            psmtDelete.setInt(1, commentId);

            return psmtDelete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (psmtDelete != null) {
                try {
                    psmtDelete.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
