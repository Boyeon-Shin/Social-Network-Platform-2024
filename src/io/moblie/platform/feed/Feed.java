package io.moblie.platform.feed;

import java.util.Date;

public class Feed {
    private int feedId;
    private String feedDetail;
    private Date timestamp;
    private String userId;

    public Feed(final int feedId, final String feedDetail, final Date timestamp, final String userId) {
        this.feedId = feedId;
        this.feedDetail = feedDetail;
        this.timestamp = timestamp;
        this.userId = userId;
    }

    public int getFeedId() {
        return feedId;
    }

    public String getFeedDetail() {
        return feedDetail;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "feedId=" + feedId +
                ", feedDetail='" + feedDetail + '\'' +
                ", timestamp=" + timestamp +
                ", userId='" + userId + '\'' +
                '}';
    }
}

