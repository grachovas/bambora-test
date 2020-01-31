package com.bambora.server.commons;

import com.bambora.server.data.notification.Notification;
import com.bambora.server.data.notification.NotificationData;
import com.bambora.server.data.notification.NotificationParameters;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class NotificationDeserializer implements JsonDeserializer<Notification> {

    private final Gson gson;

    // Map of subclasses
    private final Map<String, Class<? extends NotificationData>> dataTypes;

    public NotificationDeserializer() {
        gson = new Gson();
        dataTypes = new HashMap<>();
    }

    // Registers a notificationData subclass
    public void registerDataType(final String method, final Class<? extends NotificationData> dataTypeClass) {
        dataTypes.put(method, dataTypeClass);
    }

    @Override
    public Notification deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject request = jsonElement.getAsJsonObject();
        final JsonObject params = request.get("params").getAsJsonObject();

        final JsonObject data = params.get("data").getAsJsonObject();

        // Read the field named "Request.method"
        final JsonElement commandTypeElement = request.get("method");

        // Query the dataTypes map to instance the right subclass
        final Class<? extends NotificationData> commandInstanceClass = dataTypes.get(commandTypeElement.getAsString());

        final NotificationData dataObject = gson.fromJson(data, commandInstanceClass);
        final NotificationParameters paramsObject = gson.fromJson(params, NotificationParameters.class);
        final Notification requestObject = gson.fromJson(request, Notification.class);

        if (data.has("attributes")) {
            if (data.get("attributes").isJsonNull()) {
                dataObject.setAttributes(new HashMap<>());
            }
        }

        paramsObject.setData(dataObject);
        requestObject.setParams(paramsObject);
        return requestObject;
    }
}
