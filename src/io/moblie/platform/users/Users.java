package io.moblie.platform.users;

public class Users {
    private String userId;
    private String userName;
    private String userEmail;
    private String sex;
    private String phoneNumber;

    public Users(final String userId, final String userName, final String userEmail, final String sex, final String phoneNumber) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public String getUseName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getSex() {
        return sex;
    }

    public String PhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Users {" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail=" + userEmail +
                ", sex='" + sex + '\'' +
                ", phoneNumber='" + phoneNumber +
                '}';
    }
}


