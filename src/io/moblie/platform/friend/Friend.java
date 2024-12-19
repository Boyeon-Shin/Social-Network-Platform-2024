package io.moblie.platform.friend;

public class Friend {
    private String userId;
    private String friendId;

    public Friend(final String userId, final String friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public String getuserId() {
        return userId;
    }

    public String getfriendId() {
        return friendId;
    }

    @Override
    public String toString() {
        return "Friend {userId=" + userId + ", friendId=" + friendId + "}";
    }
}
