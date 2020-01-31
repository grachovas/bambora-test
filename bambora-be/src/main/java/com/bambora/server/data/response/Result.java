package com.bambora.server.data.response;


import com.bambora.server.commons.Method;

public class Result {
    private String signature;
    private String uuid;
    private Method method;
    private Object data;

    public String getSignature() {
        return signature;
    }

    public void setSignature(final String signature) {
        this.signature = signature;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(final Method method) {
        this.method = method;
    }

    public Object getData() {
        return data;
    }

    public void setData(final Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "\tSIGNATURE: " + signature +  "\n\tUUID: " + uuid + "\n\tMETHOD: " + method + "\n\tDATA: " + data;
    }
}
