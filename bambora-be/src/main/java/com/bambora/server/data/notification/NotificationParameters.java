package com.bambora.server.data.notification;

public class NotificationParameters {
    private String signature;
    private String uuid;
    private NotificationData data;

    public NotificationData getData() {
        return data;
    }

    public void setData(final NotificationData data) {
        this.data = data;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(final String signature) {
        this.signature = signature;
    }

    String getUUID() {
        return uuid;
    }

    public void setUUID(final String uuid) {
        this.uuid = uuid;
    }
}
