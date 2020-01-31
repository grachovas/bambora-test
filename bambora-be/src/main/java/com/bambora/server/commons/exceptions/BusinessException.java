/*
 * Copyright (C) 2016 Medtronic Diabetes.
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Medtronic Diabetes.  Confidential Information.  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Medtronic Diabetes.
 *
 * NOTES:
 */

package com.bambora.server.commons.exceptions;

import java.util.HashMap;
import java.util.Map;


public class BusinessException extends RuntimeException {
    private static final String SINGLE_PARAM_KEY = "param";
    private Map<String, String> params = new HashMap<>();
    private Map<String, String> clientParams = new HashMap<>();

    /**
     * Construct a {@link BusinessException} with a custom exception message.
     *
     * @param message exception message
     */
    public BusinessException(final String message) {
        super(message);
    }

    /**
     * Ctor with message and exception parameters for better understanding of exception.
     * params are logged when exception occurs.
     *
     * @param message exception message
     * @param params1 exception params
     */
    public BusinessException(final String message, final Map<String, String> params1) {
        super(message);
        this.params = params1;
    }

    /**
     * Ctor with exception message and single - default param.
     *
     * @param message     exception message
     * @param singleParam single param value
     */
    public BusinessException(final String message, final String singleParam) {
        super(message);
        params.put(SINGLE_PARAM_KEY, singleParam);
    }

    /**
     * Ctor with exception message and cause.
     *
     * @param message exception message
     * @param cause   exception cause
     */
    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Builder with single param.
     *
     * @param key   param key
     * @param value param value
     * @return this object
     */
    public BusinessException withParam(final String key, final String value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * Builder with client param.
     *
     * @param key   client param key
     * @param value client param value
     * @return this object
     */
    public BusinessException withClientParam(final String key, final String value) {
        this.clientParams.put(key, value);
        return this;
    }

    /**
     * Return server message (exception message + server params) for logging.
     *
     * @return server message for logging
     */
    public String getServerMessage() {
        return getMessage() + appendParamsString();
    }

    /**
     * Return client params.
     *
     * @return client params
     */
    public Map<String, String> getClientParams() {
        return clientParams;
    }

    /**
     * Returns appropriate client response status.
     * @return client response status.
     */
    /*public Response.Status getClientResponseStatus() {
        return Response.Status.BAD_REQUEST;
    }*/

    /**
     * Creates string of server params in a loggable form.
     *
     * @return server params string
     */
    private String appendParamsString() {
        if (params != null && !params.isEmpty()) {
            return "[" + params.toString() + "]";
        } else {
            return "";
        }
    }
}
