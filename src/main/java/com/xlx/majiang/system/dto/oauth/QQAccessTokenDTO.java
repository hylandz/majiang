package com.xlx.majiang.system.dto.oauth;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * access token of qq
 *
 * @author xielx at 2021/3/8 21:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QQAccessTokenDTO extends AbstractAccessToken{
    
    
    private String grant_type;
    private String fmt;
    private String refresh_token;
    
    
    public QQAccessTokenDTO(String code, String client_id, String client_secret, String redirect_uri, String grant_type, String fmt) {
        super(code, client_id, client_secret, redirect_uri);
        this.grant_type = grant_type;
        this.fmt = fmt;
    }
    
    public QQAccessTokenDTO(String client_id, String client_secret, String grant_type, String fmt, String refresh_token) {
        super(client_id, client_secret);
        this.grant_type = grant_type;
        this.fmt = fmt;
        this.refresh_token = refresh_token;
    }
}
