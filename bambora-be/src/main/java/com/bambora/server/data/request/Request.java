package com.bambora.server.data.request;


import com.bambora.server.commons.Method;

public class Request {
    private Method method;
    private RequestParameters params;
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

    public RequestParameters getParams() {
        return params;
    }

    public void setParams(final RequestParameters params) {
        this.params = params;
    }

    public String getUUID() {
        return params.getUUID();
    }
}
