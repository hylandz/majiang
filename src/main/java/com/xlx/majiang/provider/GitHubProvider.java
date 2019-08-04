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

  /**
   * 获取用于得到github用户信息的token
   * @param accessTokenDTO
   * @return str
   */
  public static String getAccessToken(AccessTokenDTO accessTokenDTO) {
    MediaType mediaType = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    //POST
    RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
    Request request = new Request.Builder()
            .url("https://github.com/login/oauth/access_token")
            .post(body)
            .build();
    try (Response response = client.newCall(request).execute()) {
      //404
      String str = response.body().string();
      String token = str.split("&")[0].split("=")[1];

      return token;
    } catch (IOException e) {
      throw new ClassCastException(e.getMessage());
    }
  }

  /*
   * 获取github用户信息
   * @param accessToken 获取的token
   * @return
   */
  public static GitHubUser getGitHubUser(String accessToken) {
    OkHttpClient client = new OkHttpClient();
    //GET
    Request request = new Request.Builder()
            .url("https://api.github.com/user?access_token=" + accessToken)
            .build();


    try(Response response = client.newCall(request).execute()){
      String strUser = response.body().string();
      GitHubUser gitHubUser = JSON.parseObject(strUser, GitHubUser.class);
      return gitHubUser;
    } catch (IOException e) {
      throw new ClassCastException(e.getMessage());
    }

  }
}
