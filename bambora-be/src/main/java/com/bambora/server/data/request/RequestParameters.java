package com.bambora.server.data.request;

import com.google.gson.annotations.SerializedName;

public class RequestParameters {
    @SerializedName("Signature")
    private String signature;
    @SerializedName("UUID")
    private String uuid;
    @SerializedName("Data")
    private RequestData data;

    public RequestData getData() {
        return data;
    }

    public void setData(final RequestData data) {
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
