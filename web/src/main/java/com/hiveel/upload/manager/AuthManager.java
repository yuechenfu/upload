package com.hiveel.upload.manager;

import com.hiveel.auth.sdk.model.Account;
import com.hiveel.auth.sdk.service.AccountService;
import com.hiveel.core.model.rest.BasicRestCode;
import com.hiveel.core.model.rest.Rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthManager {
    @Autowired
    private AccountService accountService;

    public Account findAccountByToken(String token){
        Rest<Account> rest = accountService.me(token);
        if(BasicRestCode.SUCCESS != rest.getCode()){
            return Account.NULL;
        }
        return rest.getData();
    }
}
