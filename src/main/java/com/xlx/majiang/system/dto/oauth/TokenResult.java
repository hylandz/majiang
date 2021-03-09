package com.xlx.majiang.system.dto.oauth;

import lombok.Data;

/**
 * access token 返回结果
 *
 * @author xielx at 2021/3/8 21:52
 */
@Data
public class TokenResult {
    
    
    private String access_token;
    private int expires_in;
    private String refresh_token;
}
