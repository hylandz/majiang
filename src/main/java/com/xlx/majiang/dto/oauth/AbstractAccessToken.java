package com.xlx.majiang.dto.oauth;

/**
 * 抽象的AccessToken
 *
 * @author xielx at 2020/3/8 16:06
 */
public abstract class AbstractAccessToken {
    
    /**
     * 返回时的返回值
     */
    private String code;
    /**
     * 户端id
     */
    private String client_id;
    /**
     * 客户端secret
     */
    private String client_secret;
    /**
     * 回调地址
     */
    private String redirect_uri;
    
    
    public AbstractAccessToken(String code, String client_id, String client_secret, String redirect_uri) {
        this.code = code;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.redirect_uri = redirect_uri;
    }
    
    public AbstractAccessToken(String client_id, String client_secret) {
        this.client_id = client_id;
        this.client_secret = client_secret;
    }
    
    public AbstractAccessToken() {
    
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getClient_id() {
        return client_id;
    }
    
    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
    
    public String getClient_secret() {
        return client_secret;
    }
    
    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }
    
    public String getRedirect_uri() {
        return redirect_uri;
    }
    
    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }
}
