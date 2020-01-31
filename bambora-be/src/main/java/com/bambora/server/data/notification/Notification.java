package com.bambora.server.data.notification;


import com.bambora.server.commons.Method;

public class Notification {
    private Method method;
    private NotificationParameters params;
    private double version = 1.1;

    public double getVersion() {
        return version;
    }

    public void setVersion(final double version) {
        this.version = version;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(final Method method) {
        this.method = method;
    }

    public NotificationParameters getParams() {
        return params;
    }

    public void setParams(final NotificationParameters params) {
        this.params = params;
    }

    public String getUUID() {
        return params.getUUID();
    }
}
