package com.xlx.majiang.controller;

import com.xlx.majiang.dto.AccessTokenDTO;
import com.xlx.majiang.dto.GitHubUser;
import com.xlx.majiang.mapper.UserMapper;
import com.xlx.majiang.model.User;
import com.xlx.majiang.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * github认证
 *
 * @author xielx on  2019/6/21
 * To change this template use File | Settings | Editor | File and Code Templates.
 */
@Controller
public class AuthorizeController {

  @Value("${github.client.id}")
  private String clientId;
  @Value("${github.client.secret}")
  private String clientSecret;
  @Value("${github.redirect.uri}")
  private String redirectUri;

  @Autowired
  private GitHubProvider gitHubProvider;

  @Autowired
  private UserMapper userMapper;
  @GetMapping("/callback")
  public String callback(@RequestParam(name = "code") String code,
                         @RequestParam(name = "state") String state,
                         HttpServletRequest request) {

    AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
    accessTokenDTO.setClient_id(clientId);
    accessTokenDTO.setClient_secret(clientSecret);
    accessTokenDTO.setCode(code);
    accessTokenDTO.setRedirect_uri(redirectUri);
    accessTokenDTO.setState(state);

    String  token = gitHubProvider.getAccessToken(accessTokenDTO);
    GitHubUser gitHubUser = gitHubProvider.getUser(token);
    if(gitHubUser != null && gitHubUser.getId() != null){
      User user = new User();
      user.setAccountId(String.valueOf(gitHubUser.getId()));
      user.setName(gitHubUser.getName());
      //
      user.setToken(UUID.randomUUID().toString());
      user.setBio(gitHubUser.getBio());
      user.setAvatarUrl(gitHubUser.getAvatarUrl());
      user.setGmtCreate(System.currentTimeMillis());
      userMapper.insert(user);
      request.getSession().setAttribute("user",gitHubUser);
      return "redirect:/";
    }else {
      //登录失败,重写登录
      return "redirect:/";
    }
  }
}
