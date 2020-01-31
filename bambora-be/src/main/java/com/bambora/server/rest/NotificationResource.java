package com.bambora.server.rest;


import com.bambora.server.NotificationHandler;
import com.bambora.server.commons.ResponseStatus;
import com.bambora.server.data.notification.Notification;
import com.bambora.server.data.response.TrustlyResponse;
import com.bambora.server.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class NotificationResource {

    private final Logger log = LoggerFactory.getLogger(NotificationResource.class);

    private NotificationService notificationService;

    public NotificationResource(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/notification/{id}")
    public ResponseEntity<TrustlyResponse> addNotification(@RequestBody String notification, @PathVariable String id) {
    //public ResponseEntity<Response> addNotification(@RequestBody Notification notification) {
        log.debug("REST request to add notification from Trustify");

        TrustlyResponse trustlyResponse = notificationService.processTrustlyNotification(notification, id);

        return ResponseEntity.ok().body(trustlyResponse);
    }
}
