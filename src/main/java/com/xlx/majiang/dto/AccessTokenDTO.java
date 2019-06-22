package com.xlx.majiang.dto;

/**
 * 认证访问的tokenDTO
 *
 * @Author xielx on 2019/6/21
 */
public class AccessTokenDTO {

  /**
   * 参数书写格式必须符合GitHub认证要求,
   * 错误写法:
   * clientId,clientSecret
   *
   **/
  private String client_id; // githu客户端id
  private String client_secret; //githu客户端secret
  private String code; // 返回时github的返回值
  private String redirect_uri; //授权后的跳转的url
  private String state; // 随机字符串


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

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getRedirect_uri() {
    return redirect_uri;
  }

  public void setRedirect_uri(String redirect_uri) {
    this.redirect_uri = redirect_uri;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }
}
