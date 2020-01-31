package com.bambora.server.service;


import com.bambora.server.data.response.UserInfo;
import com.bambora.server.rest.vm.DepositInfoVM;

public interface DepositService {
    String getDepositUrl(UserInfo userInfo, DepositInfoVM depositInfo);
}
