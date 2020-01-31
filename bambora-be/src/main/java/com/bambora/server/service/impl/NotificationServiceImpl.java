package com.bambora.server.service.impl;

import com.bambora.server.NotificationHandler;
import com.bambora.server.commons.ResponseStatus;
import com.bambora.server.data.notification.Notification;
import com.bambora.server.data.response.TrustlyResponse;
import com.bambora.server.repository.NotificationTraceRepository;
import com.bambora.server.service.NotificationService;
import com.bambora.server.service.mapper.NotificationTraceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private NotificationHandler notificationHandler;
    private NotificationTraceRepository notificationTraceRepository;
    private NotificationTraceMapper notificationTraceMapper;

    public NotificationServiceImpl(NotificationHandler notificationHandler,
                                   NotificationTraceRepository notificationTraceRepository,
                                   NotificationTraceMapper notificationTraceMapper) {
        this.notificationHandler = notificationHandler;
        this.notificationTraceRepository = notificationTraceRepository;
        this.notificationTraceMapper = notificationTraceMapper;
    }

    @Override
    public TrustlyResponse processTrustlyNotification(String notificationData, String id) {
        System.out.println("id: " + id);
        System.out.println(notificationData);

        log.debug("id: " + id);
        log.debug("notification: " + notificationData);

        Notification notification = notificationHandler.handleNotification(notificationData);
        notificationTraceRepository.save(notificationTraceMapper.toEntity(notification));

        //enqueueNotification(notification);
        return notificationHandler.prepareNotificationResponse(notification.getMethod(), notification.getUUID(), ResponseStatus.OK);
    }
}
