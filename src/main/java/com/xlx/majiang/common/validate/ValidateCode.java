package com.xlx.majiang.common.validate;

import java.time.LocalDateTime;

/**
 * 验证码
 *
 * @author xielx at 2020/2/11 10:34
 */
public class ValidateCode {
    
    /**
     * 验证码
     */
    private String code;
    /**
     * 有效时长
     */
    private LocalDateTime expiredTime;
    
    public ValidateCode(String code, LocalDateTime expiredTime) {
        this.code = code;
        this.expiredTime = expiredTime;
    }
    
    /**
     * 初始化实例,并设置有效时长
     * @param code 验证码
     * @param expiredTime 有效时长
     */
    public ValidateCode(String code, int expiredTime) {
        this.code = code;
        this.expiredTime = LocalDateTime.now().plusSeconds(expiredTime);
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }
    
    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }
}
