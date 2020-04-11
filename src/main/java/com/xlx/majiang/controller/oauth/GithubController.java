package com.xlx.majiang.controller.oauth;

import com.xlx.majiang.common.util.CryptoUtil;
import com.xlx.majiang.dto.oauth.GitHubAccessTokenDTO;
import com.xlx.majiang.entity.oauth.GitHubUser;
import com.xlx.majiang.entity.User;
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
   * github认证后,进行回调uri
   * @param code  认证返回的code值
   * @param state 返回原state值,用于校验
   * @return html
   */
  @GetMapping("/callbackToGithub")
  public String callback(@RequestParam(name = "code") String code,
                         @RequestParam(name = "state") String state,
                         HttpServletResponse response) {

    GitHubAccessTokenDTO accessTokenDTO = new GitHubAccessTokenDTO(code,clientId, CryptoUtil.decodeBase64(clientSecret.getBytes()),redirectUri,state);

    // 经过授权,获取access_token
    String  accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
    // 根据提供的access_token获取用户信息
    GitHubUser gitHubUser = gitHubProvider.getGitHubUser(accessToken);
    if(gitHubUser != null && gitHubUser.getId() != null) {
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
      response.addCookie(new Cookie("token", token));
  
    }
    return "redirect:/";
  }










}
