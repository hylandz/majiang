package com.xlx.majiang.system.service.impl;

import com.xlx.majiang.system.dao.AccountMapper;
import com.xlx.majiang.system.dto.ResultDTO;
import com.xlx.majiang.system.entity.Account;
import com.xlx.majiang.system.entity.AccountExample;
import com.xlx.majiang.system.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * AccountService实现类
 *
 * @author xielx at 2020/12/20 20:45
 */
@Service
public class AccountServiceImpl implements AccountService {
    
    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    
    @Resource
    private AccountMapper accountMapper;
    
    @Override
    public ResultDTO<Object> registerAccount(Account account) {
        
        accountMapper.insertSelective(account);
        
        return ResultDTO.okOf("注册成功");
    }
    
    @Override
    public void changeAccountPwd(String password) {
        Account account = new Account();
        account.setPassword(password);
        account.setGmtModified(System.currentTimeMillis());
        
        AccountExample ae = new AccountExample();
        ae.createCriteria().andPasswordEqualTo("password");
        accountMapper.updateByExampleSelective(account,ae);
    }
}
