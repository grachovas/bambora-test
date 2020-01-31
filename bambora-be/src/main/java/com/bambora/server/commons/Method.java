package com.bambora.server.commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public enum Method {
    @SerializedName("Deposit")
    @JsonProperty("Deposit")
    DEPOSIT("Deposit"),

    @SerializedName("Withdraw")
    @JsonProperty("Withdraw")
    WITHDRAW("Withdraw"),

    @SerializedName("ApproveWithdrawal")
    @JsonProperty("ApproveWithdrawal")
    APPROVE_WITHDRAWAL("ApproveWithdrawal"),

    @SerializedName("DenyWithdrawal")
    @JsonProperty("DenyWithdrawal")
    DENY_WITHDRAWAL("DenyWithdrawal"),

    @SerializedName("AccountLedger")
    @JsonProperty("AccountLedger")
    ACCOUNT_LEDGER("AccountLedger"),

    @SerializedName("ViewAutomaticSettlementDetailsCSV")
    @JsonProperty("ViewAutomaticSettlementDetailsCSV")
    VIEW_AUTOMATIC_SETTLEMENT_DETAILS_CSV("ViewAutomaticSettlementDetailsCSV"),

    @SerializedName("Balance")
    @JsonProperty("Balance")
    BALANCE("Balance"),

    @SerializedName("GetWithdrawals")
    @JsonProperty("GetWithdrawals")
    GET_WITHDRAWALS("GetWithdrawals"),

    @SerializedName("Refund")
    @JsonProperty("Refund")
    REFUND("Refund"),

    @SerializedName("credit")
    @JsonProperty("credit")
    CREDIT("credit"),

    @SerializedName("debit")
    @JsonProperty("debit")
    DEBIT("debit"),

    @SerializedName("pending")
    @JsonProperty("pending")
    PENDING("pending"),

    @SerializedName("cancel")
    @JsonProperty("cancel")
    CANCEL("cancel"),

    @SerializedName("account")
    @JsonProperty("account")
    ACCOUNT("account"),

    @SerializedName("Charge")
    @JsonProperty("Charge")
    CHARGE("Charge"),

    @SerializedName("SelectAccount")
    @JsonProperty("SelectAccount")
    SELECT_ACCOUNT("SelectAccount");

    private final String jsonName;

    Method(final String s) {
        jsonName = s;
    }

    public boolean equalsName(final String otherName) {
        return otherName != null && jsonName.equals(otherName);
    }

    public String toString() {
        return jsonName;
    }
}
