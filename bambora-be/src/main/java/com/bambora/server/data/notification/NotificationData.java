package com.bambora.server.data.notification;

import com.google.gson.annotations.SerializedName;

import java.util.Map;
import java.util.TreeMap;

public class NotificationData {
    @SerializedName("messageid")
    private String messageId;
    @SerializedName("notificationid")
    private String notificationId;
    @SerializedName("orderid")
    private String orderId;

    private Map<String, Object> attributes;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(final String notificationId) {
        this.notificationId = notificationId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(final String orderId) {
        this.orderId = orderId;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(final Map<String, Object> attributes) {
        this.attributes = new TreeMap<>(attributes);
    }
}
