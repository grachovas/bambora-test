package com.bambora.server.data.notification.notificationdata;

import com.bambora.server.data.notification.NotificationData;
import com.google.gson.annotations.SerializedName;

public class AccountNotificationData extends NotificationData {
    @SerializedName("accountid")
    private String accountId;
    private char verified;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(final String accountId) {
        this.accountId = accountId;
    }

    public boolean getVerified() {
        return charToBoolean(verified);
    }

    public void setVerified(final boolean verified) {
        this.verified = booleanToChar(verified);
    }

    private char booleanToChar(final boolean bool) {
        return bool ? '1' : '0';
    }

    private boolean charToBoolean(final char c) {
        return c == '1';
    }
}
