package com.bambora.server.data.notification.notificationdata;

import com.bambora.server.commons.Currency;
import com.bambora.server.data.notification.NotificationData;
import com.google.gson.annotations.SerializedName;

public class PendingNotificationData extends NotificationData {
    private String amount;
    private Currency currency;
    @SerializedName("enduserid")
    private String endUserId;
    private String timestamp;

    public String getAmount() {
        return amount;
    }

    public void setAmount(final String amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

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
