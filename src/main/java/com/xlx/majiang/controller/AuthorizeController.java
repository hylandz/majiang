package com.xlx.majiang.controller;

import com.alibaba.fastjson.JSON;
import com.xlx.majiang.dto.AccessTokenDTO;

import com.xlx.majiang.dto.GitHubUser;
import com.xlx.majiang.provider.GitHubProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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


  @GetMapping("/callback")
  public GitHubUser callback(@RequestParam(name = "code") String code,
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
    System.out.println("GitHub用户对象:" + gitHubUser);

    if(gitHubUser != null && gitHubUser.getId() != null){

    }
    return gitHubUser;
  }
}
