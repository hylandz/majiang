package com.xlx.majiang.system.service.impl;

import com.xlx.majiang.system.dao.AccountMapper;
import com.xlx.majiang.system.entity.Account;
import com.xlx.majiang.system.entity.AccountExample;
import com.xlx.majiang.system.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * AccountService实现类
 *
 * @author xielx at 2020/12/20 20:45
 */
@Service
public class AccountServiceImpl implements AccountService {
    
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    
    @Resource
    private AccountMapper accountMapper;
    
    @Override
    public int registerAccount(Account account) {
        account.setGmtCreate(System.currentTimeMillis());
        return accountMapper.insertSelective(account);
    }
    
    @Override
    public void changeAccountPwdByEmail(String password, String email) {
        Account account = new Account();
        account.setPassword(password);
        account.setGmtModified(System.currentTimeMillis());
        
        AccountExample ae = new AccountExample();
        ae.createCriteria().andEmailEqualTo(email);
        accountMapper.updateByExampleSelective(account,ae);
    }
    
    @Override
    public Account doLogin(String username, String password) {
        AccountExample ae = new AccountExample();
        ae.createCriteria().andUserNameEqualTo(username)
                .andPasswordEqualTo(password);
        List<Account> accounts = accountMapper.selectByExample(ae);
        if (accounts.size() > 0){
            return accounts.get(0);
        }
        
        return null;
    }
}
