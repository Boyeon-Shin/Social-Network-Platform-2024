package io.moblie.platform.users;

import io.moblie.conf.Conf;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static Users setUsers(ResultSet rs) throws SQLException {
        String userId = rs.getString("user_id");
        String userName = rs.getString("user_name");
        String userEmail = rs.getString("user_email");
        String sex = rs.getString("sex");
        String phoneNumber = rs.getString("phonenumber");

        return new Users(userId, userName, userEmail, sex, phoneNumber);
    }

    public static List<Users> selectAll() {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;

        List<Users> customerList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {

            String query = "SELECT * FROM users";

            psmtQuery = conn.prepareStatement(query);

            rs = psmtQuery.executeQuery();

            while (rs.next()) {
                Users customer = setUsers(rs);
                customerList.add(customer);
            }
            return customerList;

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

    public static Users selectById(final String userId) {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {

            String query = "SELECT * FROM users WHERE user_id = ?";

            psmtQuery = conn.prepareStatement(query);
            psmtQuery.setString(1, userId);

            rs = psmtQuery.executeQuery();

            if (rs.next()) {
                Users user = setUsers(rs);
                return user;
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


    public static int insert(String userId, String userName, String userEmail, String sex, String phoneNumber) {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;
        PreparedStatement psmtUpdate = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {

            String query = "SELECT * FROM users WHERE user_Id = ?";

            psmtQuery = conn.prepareStatement(query);
            psmtQuery.setString(1, userId);

            rs = psmtQuery.executeQuery();

            if (!rs.next()) {
                String insertStatement =
                        "INSERT INTO users (user_id, user_name, user_email, sex, phoneNumber) " +
                                "VALUES(?,?,?,?,?)";
                psmtUpdate = conn.prepareStatement(insertStatement);
                // 물음표의 위치 작성
                psmtUpdate.setString(1, userId);
                psmtUpdate.setString(2, userName);
                psmtUpdate.setString(3, userEmail);
                psmtUpdate.setString(4, sex);
                psmtUpdate.setString(5, phoneNumber);
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

    public static int updateById(String userId, String userName, String userEmail, String sex, String phoneNumber) {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;
        PreparedStatement psmtUpdate = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {

            String query = "SELECT * FROM users WHERE user_id = ?";

            psmtQuery = conn.prepareStatement(query);
            psmtQuery.setString(1, userId);

            rs = psmtQuery.executeQuery();

            if (rs.next()) {
                String updateStatement =
                        "UPDATE users " +
                                "   SET user_name = ?, user_email = ? , sex = ?, phonenumber = ? " +
                                "WHERE user_id = ?";

                psmtUpdate = conn.prepareStatement(updateStatement);
                // 물음표의 위치 작성
                psmtUpdate.setString(1, userName);
                psmtUpdate.setString(2, userEmail);
                psmtUpdate.setString(3, sex);
                psmtUpdate.setString(4, phoneNumber);
                psmtUpdate.setString(5, userId);
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

    public static int deleteById(String userId) {
        ResultSet rs = null;
        PreparedStatement psmtQuery = null;
        PreparedStatement psmtDeleteFeed = null;
        PreparedStatement psmtDeleteComment = null;
        PreparedStatement psmtDeleteUser = null;

        try (Connection conn = DriverManager.getConnection(Conf.DB_URL, Conf.DB_USER, Conf.DB_PASSWORD)) {
            String query = "SELECT * FROM comment WHERE user_id = ? ";
            psmtQuery = conn.prepareStatement(query);
            psmtQuery.setString(1, userId);

            rs = psmtQuery.executeQuery();

            if (rs.next()) {
                String deleteStatement = "DELETE FROM comment WHERE user_id = ? OR feed_id IN (Select feed_id from feed where user_id = ?)";
                psmtDeleteComment = conn.prepareStatement(deleteStatement);
                psmtDeleteComment.setString(1, userId);
                psmtDeleteComment.setString(2, userId);
                psmtDeleteComment.executeUpdate();



                String deleteStatement2 =
                        "DELETE FROM feed WHERE user_id = ? ";
                psmtDeleteFeed= conn.prepareStatement(deleteStatement2);
                psmtDeleteFeed.setString(1, userId);
                psmtDeleteFeed.executeUpdate();

                String deleteStatement3 =
                        "DELETE FROM users WHERE user_id = ? ";

                psmtDeleteUser = conn.prepareStatement(deleteStatement3);
                psmtDeleteUser.setString(1, userId);

                return psmtDeleteUser.executeUpdate();
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
