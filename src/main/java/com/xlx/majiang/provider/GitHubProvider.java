package com.xlx.majiang.provider;

import com.alibaba.fastjson.JSON;
import com.xlx.majiang.dto.AccessTokenDTO;
import com.xlx.majiang.dto.GitHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * github信息
 *
 * @Author xielx on 2019/6/21
 */
@Component
public class GitHubProvider {

  /*
   * 获取AccessTokenDTO对象
   * @param accessTokenDTO
   * @return
   */
  public String getAccessToken(AccessTokenDTO accessTokenDTO) {
    MediaType mediaType = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
    Request request = new Request.Builder()
            .url("https://github.com/login/oauth/access_token")
            .post(body)
            .build();
    try (Response response = client.newCall(request).execute()) {
      //404
      String str = response.body().string();
      String token = str.split("&")[0].split("=")[1];
      System.out.println("access_token的github响应:" + str);
      return token;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;

  }

  /*
   * 使用github返回的token获取github用户信息
   * @param accessToken 获取的token
   * @return
   */
  public GitHubUser getUser(String accessToken) {
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
            .url("https://api.github.com/user?access_token=" + accessToken)
            .build();


    try(Response response = client.newCall(request).execute()){
      String strUser = response.body().string();
      GitHubUser gitHubUser = JSON.parseObject(strUser, GitHubUser.class);
      return gitHubUser;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;

  }
}
