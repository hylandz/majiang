package com.xlx.majiang.provider;

import com.alibaba.fastjson.JSON;
import com.xlx.majiang.dto.AccessTokenDTO;
import com.xlx.majiang.dto.GitHubUser;
import com.xlx.majiang.exception.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * github信息
 *
 * @author xielx on 2019/6/21
 */
@Slf4j
@Component
public class GitHubProvider {

  
  private final String OAUTH_API = "https://github.com/login/oauth/access_token";
  /**
   * 授权 ,获取访问信息的access_token
   * @param accessTokenDTO 封装查询参数
   * @return str
   */
  public  String getAccessToken(AccessTokenDTO accessTokenDTO) {
    MediaType mediaType = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    // POST方式
    RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
    Request request = new Request.Builder()
            .url(OAUTH_API)
            .post(body)
            .build();
    try (Response response = client.newCall(request).execute()) {
      //404
      ResponseBody responseBody = response.body();
      if (responseBody == null){
        log.error("获取响应体失败~~~~~~");
        throw new CustomizeException("获取响应体失败[{}]");
      }
      String str = responseBody.string();
      return str.split("&")[0].split("=")[1];

    } catch (IOException e) {
      log.error("获取token失败:[{}]",e.getMessage());
      throw new ClassCastException(e.getMessage());
    }
  }

  /**
   * 获取github用户信息
   * @param accessToken 授权的token
   * @return 封装的用户信息
   */
  public  GitHubUser getGitHubUser(String accessToken) {
    OkHttpClient client = new OkHttpClient();
    // GET方式
    Request request = new Request.Builder()
            .url("https://api.github.com/user?access_token=" + accessToken)
            .build();


    try(Response response = client.newCall(request).execute()){
      String strUser = response.body().string();
      return JSON.parseObject(strUser, GitHubUser.class);
    } catch (IOException e) {
      throw new ClassCastException(e.getMessage());
    }

  }
}
