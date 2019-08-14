package com.xlx.majiang.controller;

import com.xlx.majiang.dto.AccessTokenDTO;
import com.xlx.majiang.dto.GitHubUser;
import com.xlx.majiang.model.User;
import com.xlx.majiang.provider.GitHubProvider;
import com.xlx.majiang.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * github认证
 *
 * @author xielx on  2019/6/21
 * To change this template use File | Settings | Editor | File and Code Templates.
 */
@Slf4j
@Controller
public class GithubController {

  @Value("${github.client.id}")
  private String clientId;
  @Value("${github.client.secret}")
  private String clientSecret;
  @Value("${github.redirect.uri}")
  private String redirectUri;



  @Resource
  private GitHubProvider gitHubProvider;

  @Resource
  private UserService userService;




  /**
   *  github认证后,获取github用户信息
   * @param code .
   * @param state .
   * @param response .
   * @return str
   */
  @GetMapping("/callback")
  public String callback(@RequestParam(name = "code") String code,
                         @RequestParam(name = "state") String state,
                         HttpServletResponse response) {

    AccessTokenDTO accessTokenDTO = new AccessTokenDTO(clientId,clientSecret,code,redirectUri,state);

    String  accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
    GitHubUser gitHubUser = gitHubProvider.getGitHubUser(accessToken);
    if(gitHubUser != null && gitHubUser.getId() != null){
      User user = new User();
      user.setAccountId(String.valueOf(gitHubUser.getId()));
      user.setName(gitHubUser.getName());
      //
      String token = UUID.randomUUID().toString();
      user.setToken(token);
      user.setBio(gitHubUser.getBio());
      user.setAvatarUrl(gitHubUser.getAvatarUrl());
      //
      userService.createOrUpdate(user);

      //添加cookie
      response.addCookie(new Cookie("token",token));

      return "redirect:/";
    }else {
      //登录失败,重写登录
      return "redirect:/";
    }
  }










}
