package com.bambora.server.data.request;

import com.google.gson.annotations.SerializedName;

import java.util.Map;
import java.util.TreeMap;

public abstract class RequestData {
    @SerializedName("Username")
    private String username;
    @SerializedName("Password")
    private String password;
    @SerializedName("Attributes")
    private Map<String, Object> attributes = new TreeMap<>();

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setAttributes(final Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
