package com.bambora.server.rest;

import com.bambora.server.data.response.UrlResponse;
import com.bambora.server.data.response.UserInfo;
import com.bambora.server.rest.vm.DepositInfoVM;
import com.bambora.server.service.DepositService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class DepositResource {

    private final Logger log = LoggerFactory.getLogger(DepositResource.class);

    private DepositService depositService;

    public DepositResource(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/url")
    public ResponseEntity<UrlResponse> getTrustlyDepositURL(@Valid @RequestBody DepositInfoVM request) {
        log.debug("REST request to get url for deposit processing");

        // simulate getting user data from security context
        UserInfo user = getUser();

        String redirectUrl;
        try {
            redirectUrl = depositService.getDepositUrl(user, request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().body(new UrlResponse(redirectUrl));
    }

    // mock data to current user info from security context
    private UserInfo getUser(){
        return new UserInfo(
                "mock_id",
                "mock_name",
                "mock_lastname",
                "mock_email@asd.com");
    }
}
