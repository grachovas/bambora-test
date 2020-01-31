package com.bambora.server.service.mapper;

import com.bambora.server.data.notification.Notification;
import com.bambora.server.data.notification.NotificationData;
import com.bambora.server.domain.NotificationTrace;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

@Service
public class NotificationTraceMapper {

    public NotificationTrace toEntity(Notification notification) {
        Gson gson = new Gson();
        NotificationTrace notificationTrace = new NotificationTrace();
        notificationTrace.setMethod(notification.getMethod());
        notificationTrace.setUuid(notification.getUUID());
        notificationTrace.setSignature(notification.getParams().getSignature());
        notificationTrace.setData(gson.toJson(notification.getParams().getData(), NotificationData.class));
        System.out.println("TEST DATA LENGTH");
        System.out.println("sign length: " + notificationTrace.getSignature().length());
        System.out.println("data length: " + notificationTrace.getData().length());
        return notificationTrace;
    }
}
