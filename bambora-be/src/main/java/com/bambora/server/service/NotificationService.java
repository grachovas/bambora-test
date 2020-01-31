package com.bambora.server.service;

import com.bambora.server.data.response.TrustlyResponse;

public interface NotificationService {
    TrustlyResponse processTrustlyNotification(String data, String id);
}
