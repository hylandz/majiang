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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * github认证
 *
 * @author xielx on  2019/6/21
 * To change this template use File | Settings | Editor | File and Code Templates.
 */
@Controller
public class AuthorizeController {

/*  @Value("")
  private String clientId;
  @Value("")
  private String clientSecret;
  @Value("")
  private String redirectUri;*/

  @Autowired
  private GitHubProvider gitHubProvider;

  @ResponseBody
  @GetMapping("/callback")
  public GitHubUser callback(@RequestParam(name = "code") String code,
                         @RequestParam(name = "state") String state,
                         HttpServletRequest request) {

    AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
    accessTokenDTO.setClient_id("c4bb3fcaa494d4a6a120");
    accessTokenDTO.setClient_secret("b9ec5998dc750f3b732a09a5b14d92b56a471d5b");
    accessTokenDTO.setCode(code);
    accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
    accessTokenDTO.setState(state);

    String  token = gitHubProvider.getAccessToken(accessTokenDTO);
    GitHubUser gitHubUser = gitHubProvider.getUser(token);
    System.out.println("GitHub用户对象:" + gitHubUser);
    return gitHubUser;
  }
}
