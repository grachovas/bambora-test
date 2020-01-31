package com.bambora.server.requestbuilders;


import com.bambora.server.commons.Method;
import com.bambora.server.commons.ResponseStatus;
import com.bambora.server.data.response.TrustlyResponse;
import com.bambora.server.data.response.Result;

import java.util.HashMap;
import java.util.Map;

public class NotificationResponse {
    private final TrustlyResponse trustlyResponse = new TrustlyResponse();

    private NotificationResponse(final Build builder) {
        final Result result = new Result();
        result.setUuid(builder.uuid);
        result.setData(builder.data);
        result.setMethod(builder.method);

        trustlyResponse.setResult(result);
        trustlyResponse.setVersion("1.1");
    }

    public TrustlyResponse getTrustlyResponse() {
        return trustlyResponse;
    }

    public static class Build {
        private final Map<String, Object> data = new HashMap<>();
        final String uuid;
        final Method method;

        public Build(final Method method, final String uuid, final ResponseStatus status) {
            this.uuid = uuid;
            this.method = method;
            data.put("status", status);
        }

        public TrustlyResponse getResponse() {
            return new NotificationResponse(this).getTrustlyResponse();
        }
    }
}
