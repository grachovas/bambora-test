package com.bambora.server;

import com.bambora.server.commons.Method;
import com.bambora.server.commons.NotificationDeserializer;
import com.bambora.server.commons.ResponseStatus;
import com.bambora.server.commons.exceptions.TrustlySignatureException;
import com.bambora.server.data.notification.Notification;
import com.bambora.server.data.notification.notificationdata.*;
import com.bambora.server.data.response.TrustlyResponse;
import com.bambora.server.requestbuilders.NotificationResponse;
import com.bambora.server.security.SignatureHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

@Service
public class NotificationHandler {

    private final SignatureHandler signatureHandler;

    public NotificationHandler(SignatureHandler signatureHandler) {
        this.signatureHandler = signatureHandler;
    }

    /**
     * Deserializes and verifies incoming notification.
     * @param notificationJson Notification sent from Trustly.
     * @return Request object, a deserialized notification.
     */
    public Notification handleNotification(final String notificationJson) {
        final NotificationDeserializer deserializer = new NotificationDeserializer();

        deserializer.registerDataType(Method.CREDIT.toString(), CreditData.class);
        deserializer.registerDataType(Method.ACCOUNT.toString(), AccountNotificationData.class);
        deserializer.registerDataType(Method.CANCEL.toString(), CancelNotificationData.class);
        deserializer.registerDataType(Method.DEBIT.toString(), DebitNotificationData.class);
        deserializer.registerDataType(Method.PENDING.toString(), PendingNotificationData.class);

        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Notification.class, deserializer)
                .create();

        final Notification notification = gson.fromJson(notificationJson, Notification.class);

        //
        verifyNotification(notification);

        return notification;
    }
    public void handleNotification(final Notification notification) {
        verifyNotification(notification);
    }

    private void verifyNotification(final Notification notification) {
        if (!signatureHandler.verifyNotificationSignature(notification)) {
            throw new TrustlySignatureException("Incoming data signature is not valid");
        }
    }

    /**
     * Creates a response for an incoming notification.
     * @param method method of the notification
     * @param uuid UUID of the incoming notification
     * @param status OK/FAIL
     * @return Notification response
     */
    public TrustlyResponse prepareNotificationResponse(final Method method, final String uuid, final ResponseStatus status) {
        final TrustlyResponse trustlyResponse = new NotificationResponse
                .Build(method, uuid, status)
                .getResponse();

        signatureHandler.signNotificationResponse(trustlyResponse);

        return trustlyResponse;
    }

    public String toJson(final TrustlyResponse trustlyResponse) {
        return new Gson().toJson(trustlyResponse);
    }
}
