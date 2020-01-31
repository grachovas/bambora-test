package com.bambora.server.data.request.requestdata;

import com.bambora.server.data.request.RequestData;
import com.google.gson.annotations.SerializedName;

public class DepositData extends RequestData {
    @SerializedName("NotificationURL")
    private String notificationURL;
    @SerializedName("EndUserID")
    private String endUserID;
    @SerializedName("MessageID")
    private String messageID;

    public String getNotificationURL() {
        return notificationURL;
    }

    public void setNotificationURL(final String notificationURL) {
        this.notificationURL = notificationURL;
    }

    public String getEndUserID() {
        return endUserID;
    }

    public void setEndUserID(final String endUserID) {
        this.endUserID = endUserID;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(final String messageID) {
        this.messageID = messageID;
    }
}
