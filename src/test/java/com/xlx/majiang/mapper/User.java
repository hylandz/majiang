package com.xlx.majiang.mapper;

/**
 * 用户
 *
 * @author xielx on 2019/6/22
 */
public class User {

  private Long id;
  //账户id
  private String accountId;
  //登录名称
  private String name;
  //token
  private String token;
  //个性签名
  private String bio;
  //头像路径
  private String avatarUrl;
  //创建
  private Long gmtCreate;
  //修改
  private Long gmtModified;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
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

  public Long getGmtCreate() {
    return gmtCreate;
  }

  public void setGmtCreate(Long gmtCreate) {
    this.gmtCreate = gmtCreate;
  }

  public Long getGmtModified() {
    return gmtModified;
  }

  public void setGmtModified(Long gmtModified) {
    this.gmtModified = gmtModified;
  }
}
