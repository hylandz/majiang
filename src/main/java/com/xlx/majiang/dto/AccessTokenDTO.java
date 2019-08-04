package com.xlx.majiang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 认证访问的tokenDTO
 * @data 注解:
 * 相当于@ToString、@Getter、@Setter、@EqualsAndHashCode、@RequiredArgsConstructor(构造)
 * @Author xielx on 2019/6/21
 */
@Data
@AllArgsConstructor
public class AccessTokenDTO {

  /**
   * 参数书写格式必须符合GitHub认证要求,
   * 错误写法:
   * clientId,clientSecret
   *
   **/
  private String client_id; // github客户端id
  private String client_secret; //github客户端secret
  private String code; // 返回时github的返回值
  private String redirect_uri; //授权后的跳转的url
  private String state; // 随机字符串



}
