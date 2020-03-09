package com.xlx.majiang.provider;

import com.alibaba.fastjson.JSON;
import com.xlx.majiang.dto.oauth.GitHubAccessTokenDTO;
import com.xlx.majiang.entity.oauth.GitHubUser;
import com.xlx.majiang.enums.ErrorCodeEnum;
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
  
  
  /**
   * step2:获取访问信息的access_token
   * @param accessTokenDTO 封装查询参数
   * @return str
   */
  public  String getAccessToken(GitHubAccessTokenDTO accessTokenDTO) {
    MediaType mediaType = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    // POST方式
    RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
    String access_token_api = "https://github.com/login/oauth/access_token";
    Request request = new Request.Builder()
            .url(access_token_api)
            .post(body)
            .build();
    try (Response response = client.newCall(request).execute()) {
      //404
      ResponseBody responseBody = response.body();
      if (!response.isSuccessful() || responseBody == null){
        log.error("获取响应体失败~~~~~~");
        throw new CustomizeException("获取响应体失败");
      }
      String result = responseBody.string();
      return result.split("&")[0].split("=")[1];

    } catch (IOException e) {
      log.error("获取token失败:[{}]",e.getMessage());
      throw new ClassCastException(e.getMessage());
    }
  }

  /**
   * step3:获取github用户信息
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
      ResponseBody body = response.body();
      if (!response.isSuccessful() || body == null){
        return new GitHubUser();
      }
      String strUser = body.string();
      return JSON.parseObject(strUser, GitHubUser.class);
    } catch (IOException e) {
      throw new CustomizeException(ErrorCodeEnum.GET_USER_INFO_FAILED);
    }

  }
}
