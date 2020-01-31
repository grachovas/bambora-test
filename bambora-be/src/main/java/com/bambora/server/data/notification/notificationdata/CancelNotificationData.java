package com.bambora.server.data.notification.notificationdata;

import com.bambora.server.data.notification.NotificationData;
import com.google.gson.annotations.SerializedName;

public class CancelNotificationData extends NotificationData {
    @SerializedName("enduserid")
    private String endUserId;
    private String timestamp;

    public String getEndUserId() {
        return endUserId;
    }

    public void setEndUserId(final String endUserId) {
        this.endUserId = endUserId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final String timestamp) {
        this.timestamp = timestamp;
    }
}
