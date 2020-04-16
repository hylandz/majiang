package com.xlx.majiang.system.entity.oauth;

import lombok.Data;

/**
 * github用户信息
 *
 * @Author xielx on 2019/6/21
 */
@Data
public class GitHubUser {

  //id
  private Long id;
  // 用户名称
  private String name;

  //个性签名
  private String bio;

  //用户头像
  private String avatarUrl;


}
