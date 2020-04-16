package com.xlx.majiang.system.dto.oauth;

/**
 * 认证访问的tokenDTO
 * @Author xielx on 2019/6/21
 */
public class GiteeAccessTokenDTO  extends AbstractAccessToken {

  /**
   * 固定值
   */
  private String grant_type;
  /**
   * 随机字符串
   */
  private String state;
  
  /**
   * 刷新过期的token
   */
  private String refresh_token;
  
  /** 获取access_token的参数*/
  public GiteeAccessTokenDTO(String code, String client_id, String client_secret, String redirect_uri, String grant_type, String state) {
    super(code, client_id, client_secret, redirect_uri);
    this.grant_type = grant_type;
    this.state = state;
  }
  
  // 刷新token的参数
  public GiteeAccessTokenDTO(String client_id, String client_secret, String grant_type,String refresh_token) {
    super(client_id, client_secret);
    this.grant_type = grant_type;
    this.refresh_token = refresh_token;
  }
  
  public GiteeAccessTokenDTO(String grant_type, String state) {
    this.grant_type = grant_type;
    this.state = state;
  }
  
  public String getGrant_type() {
    return grant_type;
  }
  
  public void setGrant_type(String grant_type) {
    this.grant_type = grant_type;
  }
  
  public String getState() {
    return state;
  }
  
  public void setState(String state) {
    this.state = state;
  }
}
