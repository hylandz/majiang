package com.xlx.majiang.system.service;

import com.xlx.majiang.system.entity.Account;

/**
 * 账户注册
 *
 * @author xielx at 2020/12/20 20:44
 */
public interface AccountService {
    
    
    /**
     * 注册账户
     * @param account Account
     * @return
     */
    int registerAccount(Account account);
    
    /**
     * 修改密码
     * @param password 新密码
     * @param email
     */
    void changeAccountPwdByEmail(String password,String email);
    
    Account doLogin(String username,String password);
}
