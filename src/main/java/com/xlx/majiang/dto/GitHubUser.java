package com.xlx.majiang.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * github用户信息
 *
 * @Author xielx on 2019/6/21
 */
public class GitHubUser {

  //id
  private Long id;
  // 用户名称
  private String name;

  //个性签名
  private String bio;

  //github用户头像
  private String avatarUrl; // https://avatars0.githubusercontent.com/u/41381772?v=4

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public void setAvatarUrl(String avatarUrl) {
    this.avatarUrl = avatarUrl;
  }
}
