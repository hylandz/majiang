package com.xlx.majiang.system.entity.oauth;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * User
 * @author xielx at 2020/4/16 10:56
 */
@Data
public class JwtUser {
    
    
    /**
     * id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;
    
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    
    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;
    
    /**
     * 权限
     */
    private List<String> perms;
    /**
     * 真盐(salt=username+salt)
     * @return
     */
    private String getCredentialsSalt(){
        return username + salt;
    }
}
