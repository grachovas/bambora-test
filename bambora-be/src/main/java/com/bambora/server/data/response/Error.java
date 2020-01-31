package com.bambora.server.data.response;

public class Error {
    private String name;
    private int code;
    private String message;
    private ErrorBody error;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public ErrorBody getError() {
        return error;
    }

    public void setError(final ErrorBody error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "NAME: " + name
                + "\nCODE: " + code
                + "\nMESSAGE: " + message
                + "\nERROR BODY: " + (error != null ? error.toString() : null);
    }
}
