package com.bambora.server.security;

import com.bambora.server.commons.exceptions.TrustlyAPIException;
import com.bambora.server.commons.exceptions.TrustlyDataException;
import com.bambora.server.commons.exceptions.TrustlySignatureException;
import com.bambora.server.data.notification.Notification;
import com.bambora.server.data.request.AttributeData;
import com.bambora.server.data.request.Request;
import com.bambora.server.data.request.RequestData;
import com.bambora.server.data.response.ErrorBody;
import com.bambora.server.data.response.TrustlyResponse;
import com.bambora.server.data.response.Result;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.*;
import java.util.*;

@Service
public class SignatureHandler {

    @Value( "${application.trustly.username}" )
    private String username;

    @Value( "${application.trustly.password}" )
    private String password;


    private final Base64.Encoder base64Encoder = Base64.getEncoder();
    private final Base64.Decoder base64Decoder = Base64.getDecoder();
    private KeyChain keyChain;

    public SignatureHandler(KeyChain keyChain) {
        this.keyChain = keyChain;
    }

    /**
     * Inserts the api credentials into the given request.
     * @param request Request in which the credentials are inserted.
     */
    public void insertCredentials(final Request request) {
        request.getParams().getData().setUsername(username);
        request.getParams().getData().setPassword(password);
    }

    /**
     * Creates a signature for given request.
     * @param request Request to sign.
     */
    public void signRequest(final Request request) {
        insertCredentials(request);
        final RequestData requestData = request.getParams().getData();
        final String requestMethod = request.getMethod().toString();
        final String uuid = request.getUUID();
        final String plainText = String.format("%s%s%s", requestMethod, uuid, serializeData(requestData));

        final String signedData = createSignature(plainText);

        request.getParams().setSignature(signedData);
    }

    /**
     * Creates a signature for given notification response.
     * @param trustlyResponse The notification response to sign.
     */
    public void signNotificationResponse(final TrustlyResponse trustlyResponse) {
        final String requestMethod = trustlyResponse.getResult().getMethod().toString();
        final String uuid = trustlyResponse.getUUID();
        final Object data = trustlyResponse.getResult().getData();
        final String plainText = String.format("%s%s%s", requestMethod, uuid, serializeObject(data));

        final String signedData = createSignature(plainText);

        trustlyResponse.getResult().setSignature(signedData);
    }

    private String createSignature(final String plainText) {
        try {
            final Signature signatureInstance = Signature.getInstance("SHA1withRSA");
            signatureInstance.initSign(keyChain.getMerchantPrivateKey());
            signatureInstance.update(plainText.getBytes("UTF-8"));

            final byte[] signature = signatureInstance.sign();
            return base64Encoder.encodeToString(signature);
        }
        catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new TrustlySignatureException(e);
        }
        catch (final InvalidKeyException e) {
            throw new TrustlySignatureException("Invalid private key", e);
        }
        catch (final SignatureException e) {
            throw new TrustlySignatureException("Failed to create signature", e);
        }
    }

    private String serializeData(final Object data) {
        return serializeData(data, true);
    }

    private String serializeData(final Object data, final boolean serializeNullMap) {
        try {
            //Sort all fields found in the data object class
            final List<Field> fields = getAllFields(new LinkedList<>(), data.getClass());
            fields.sort(Comparator.comparing(Field::getName));

            //Get values using reflection
            final StringBuilder builder = new StringBuilder();
            for (final Field field : fields) {

                if (field.get(data) == null && data instanceof AttributeData) {
                    continue;
                }

                final String jsonFieldName;
                if (field.isAnnotationPresent(SerializedName.class)) {
                    jsonFieldName = field.getAnnotation(SerializedName.class).value();
                }
                else {
                    jsonFieldName = field.getName();
                }

                if (field.getType().equals(Map.class)) {
                    if (serializeNullMap) {
                        builder.append(jsonFieldName);
                        if (field.get(data) != null) {
                            builder.append(serializeObject(field.get(data)));
                        }
                        continue;
                    }
                    else {
                        if (field.get(data) != null) {
                            builder.append(jsonFieldName);
                            builder.append(serializeObject(field.get(data)));
                        }
                        continue;
                    }
                }

                builder.append(jsonFieldName);

                if (field.get(data) != null) {
                    builder.append(field.get(data));
                }
            }
            return builder.toString();
        }
        catch (final IllegalAccessException e) {
            throw new TrustlyAPIException("Failed to serialize data", e);
        }
    }

    private String serializeObject(final Object object) {
        final StringBuilder builder = new StringBuilder();

        if (object instanceof TreeMap || object instanceof LinkedTreeMap || object instanceof Map) {
            populateStringBuilder(builder, (Map) object);
        }
        else if (object instanceof ArrayList) {
            for (final Object mapEntry : (ArrayList) object) {
                populateStringBuilder(builder, (Map) mapEntry);
            }
        }
        else {
            throw new RuntimeException("Unhandled class of object: " + object.getClass());
        }

        return builder.toString();
    }

    private void populateStringBuilder(final StringBuilder builder, final Map mapEntry) {
        final List<String> strings = new ArrayList<String>(mapEntry.keySet());
        Collections.sort(strings);
        for (final String key : strings) {
            builder.append(key);
            final Object data = mapEntry.get(key);

            if (data != null) {
                if (data instanceof AttributeData) {
                    builder.append(serializeData(data));
                }
                else {
                    builder.append(data);
                }
            }
        }
    }

    /**
     * Returns a list of declared fields for given class.
     * @param fields List to add found fields
     * @param type Type of class
     * @return Given list filled with fields of given class.
     */
    private List<Field> getAllFields(List<Field> fields, final Class<?> type) {
        for (final Field field: type.getDeclaredFields()) {
            field.setAccessible(true);
            fields.add(field);
        }

        if (type.getSuperclass() != null) {
            fields = getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    /**
     * Verifies the signature of an incoming response.
     * @param trustlyResponse The response to verify
     * @return true if signature is valid
     */
    public boolean verifyResponseSignature(final TrustlyResponse trustlyResponse)  {
        final String method;
        String uuid;
        final String serializedData;
        final String signatureBase64;

        if (trustlyResponse.successfulResult()) {
            final Result result = trustlyResponse.getResult();
            method = result.getMethod() == null ? "" : result.getMethod().toString();
            uuid = result.getUuid();
            serializedData = serializeObject(result.getData());
            signatureBase64 = result.getSignature();
        }
        else {
            final ErrorBody error = trustlyResponse.getError().getError();
            method = error.getMethod() == null ? "" : error.getMethod().toString();
            uuid = error.getUuid();
            serializedData = serializeData(error.getData());
            signatureBase64 = error.getSignature();
        }

        if (uuid == null) {
            uuid = "";
        }

        return performSignatureVerification(method, uuid, serializedData, signatureBase64);
    }

    /**
     * Verifies the signature of an incoming notification.
     * @param notification The notification to verify
     * @return true if signature is valid
     */
    public boolean verifyNotificationSignature(final Notification notification) {
        final String method = notification.getMethod().toString();
        final String uuid = notification.getUUID();
        final String serializedData = serializeData(notification.getParams().getData(), false);
        final String signatureBase64 = notification.getParams().getSignature();

        return performSignatureVerification(method, uuid, serializedData, signatureBase64);
    }

    private boolean performSignatureVerification(final String method, final String uuid, final String serializedData, final String responseSignature) {
        try {
            final byte[] signature = base64Decoder.decode(responseSignature);
            final Signature signatureInstance = Signature.getInstance("SHA1withRSA");
            signatureInstance.initVerify(keyChain.getTrustlyPublicKey());
            final String expectedPlainText = String.format("%s%s%s", method, uuid, serializedData);
            signatureInstance.update(expectedPlainText.getBytes("UTF-8"));
            return signatureInstance.verify(signature);
        }
        catch (final IOException e) {
            throw new TrustlySignatureException("Failed to decode signature", e);
        }
        catch (final NoSuchAlgorithmException e) {
            throw new TrustlySignatureException(e);
        }
        catch (final InvalidKeyException e) {
            throw new TrustlySignatureException("Invalid public key", e);
        }
        catch (final SignatureException e) {
            throw new TrustlySignatureException("Failed to verify signature", e);
        }
    }

    /**
     * Generates a new UUID (Universally Unique Identifier) based on UUID v4
     * @return a random generated UUID in form of xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
     * where x is a hexadecimal digit (0-9 and A-F)
     */
    public static String generateNewUUID() {
        return UUID.randomUUID().toString();
    }

    public void verifyResponse(final TrustlyResponse trustlyResponse, final String requestUUID) {
        if (!verifyResponseSignature(trustlyResponse)) {
            throw new TrustlySignatureException("Incoming data signature is not valid");
        }
        if(trustlyResponse.getUUID() != null && !trustlyResponse.getUUID().equals(requestUUID) ) {
            throw new TrustlyDataException("Incoming data signature is not valid");
        }
    }
}
