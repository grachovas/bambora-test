package com.bambora.server.service.impl;

import com.bambora.server.commons.SecureRandomString;
import com.bambora.server.commons.exceptions.BusinessException;
import com.bambora.server.data.request.Request;
import com.bambora.server.data.response.TrustlyResponse;
import com.bambora.server.requestbuilders.Deposit;
import com.bambora.server.data.response.UserInfo;
import com.bambora.server.rest.vm.DepositInfoVM;
import com.bambora.server.security.SignatureHandler;
import com.bambora.server.service.DepositService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.UUID;


@Service
public class DepositServiceImpl implements DepositService {

    @Value("${application.trustly.environment.url}")
    private String environmentUrl;

    @Value("${application.trustly.notification.path}")
    private String notificationPath;

    @Value("${application.domain.url}")
    private String domainUrl;

    private final SignatureHandler signatureHandler;


    public DepositServiceImpl(SignatureHandler signatureHandler) {
        this.signatureHandler = signatureHandler;
    }

    @Override
    public String getDepositUrl(UserInfo userInfo, DepositInfoVM depositInfo) {

        String notificationPathParam = new SecureRandomString(25).nextString();

        // Build request
        Request deposit = new Deposit
                .Build(domainUrl + notificationPath + notificationPathParam,
                    userInfo.getId(),
                    UUID.randomUUID().toString(),
                    depositInfo.getCurrency(),
                    userInfo.getFirstName(),
                    userInfo.getLastName(),
                    userInfo.getEmail())
                .successURL(domainUrl + "success-payment")
                .failURL(domainUrl + "failed-payment")
                .getRequest();


        // Sign request
        signatureHandler.signRequest(deposit);
        String signedJson = new Gson().toJson(deposit, Request.class);

        RestTemplate restTemplate = new RestTemplate();
        TrustlyResponse trustlyResponse = restTemplate.postForObject(environmentUrl, signedJson, TrustlyResponse.class);

        signatureHandler.verifyResponse(trustlyResponse, deposit.getUUID());

        if (trustlyResponse == null) {
            throw new BusinessException("Error occured during connection to Trustify");
        }
        else if (trustlyResponse.getError() != null) {
            throw new BusinessException(trustlyResponse.getError().getMessage());
        }
        else {
            if (trustlyResponse.getResult().getData() == null) {
                throw new BusinessException("No redirect url found in Trustly response.");
            }
        }

        return getRedirectUri(trustlyResponse.getResult().getData());
    }

    private String getRedirectUri(Object data) {
        String url = "";
        if (data instanceof LinkedHashMap){
            LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) data;
            url = (String) map.get("url");
        }
        if (url == null || url.isEmpty()){
            throw new BusinessException("No redirect url found in Trustly response. ");
        }
        return url;
    }
}
