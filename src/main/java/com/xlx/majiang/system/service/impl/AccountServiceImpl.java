package com.xlx.majiang.system.service.impl;

import com.xlx.majiang.system.dao.AccountMapper;
import com.xlx.majiang.system.dto.ResultDTO;
import com.xlx.majiang.system.entity.Account;
import com.xlx.majiang.system.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * AccountService实现类
 *
 * @author xielx at 2020/12/20 20:45
 */
@Service
public class AccountServiceImpl implements AccountService {
    
    private static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    
    @Autowired
    private AccountMapper accountMapper;
    
    @Override
    public ResultDTO<Object> registerAccount(Account account) {
        
        accountMapper.insertSelective(account);
        
        return ResultDTO.okOf("注册成功");
    }
}
