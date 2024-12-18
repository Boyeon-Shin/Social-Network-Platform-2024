package io.moblie.platform.comment;

import java.util.Date;

public class Comment {
    private int commentId;
    private String commentDetail;
    private Date timestamp;
    private String userId;

    public Comment(final int commentId, final String commentDetail, final Date timestamp, final String userId) {
        this.commentId = commentId;
        this.commentDetail = commentDetail;
        this.timestamp = timestamp;
        this.userId = userId;
    }

    public int getCommentId() {
        return commentId;
    }

    public String getCommentDetail() {
        return commentDetail;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", commentDetail='" + commentDetail + '\'' +
                ", timestamp=" + timestamp +
                ", userId='" + userId + '\'' +
                '}';
    }
}
