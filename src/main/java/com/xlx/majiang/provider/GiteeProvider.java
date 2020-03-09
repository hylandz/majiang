package com.xlx.majiang.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xlx.majiang.dto.oauth.GiteeAccessTokenDTO;
import com.xlx.majiang.entity.oauth.GiteeUser;
import com.xlx.majiang.enums.ErrorCodeEnum;
import com.xlx.majiang.exception.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 码云
 *
 * @author xielx on 2019/8/4
 */
@Slf4j
@Component
public class GiteeProvider {
    
    
    /**
     * 获取访问密钥
     *
     * @param accessTokenDTO obj
     * @return str
     */
    public String getAccessToken(GiteeAccessTokenDTO accessTokenDTO) {
        
        OkHttpClient client = new OkHttpClient();
        
        //2.创建传参的请求体,及数据类型json
        MediaType mediaType = MediaType.get("application/json;charset=utf-8");
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        String access_token_api = "https://gitee.com/oauth/token";
        //3.POST请求
        Request request = new Request.Builder()
                                  .url(access_token_api)
                                  .post(body)
                                  .build();
        
        //4.执行
        try (Response response = client.newCall(request).execute()) {
            //5.获取响应体内容
            ResponseBody body1 = response.body();
            String str;
            if (body1 != null){
                str = body1.string();
                JSONObject object = JSONObject.parseObject(str);
                return object.getString("access_token");
            }
            return "";
        } catch (IOException e) {
            log.error("获取token失败:[{}]", e.getMessage());
            throw new ClassCastException(e.getMessage());
        }
    }
    
    
    /**
     * 获取授权的用户资料
     *
     * @param access_token 授权码99a0da14fad6c56ce73af1ffa03ec84f
     * @return .
     */
    public  GiteeUser getUserInfo(String access_token) {
        
        //1.创建客户端
        OkHttpClient client = new OkHttpClient();
        //2.创建GET请求
        String GET_USER_API = "https://gitee.com/api/v5/user?access_token=";
        Request request = new Request.Builder()
                                  .url(GET_USER_API + access_token)
                                  .addHeader("Content-Type", "application/json;charset=UTF-8")
                                  .build();
        
        //3.执行请求
        try (Response response = client.newCall(request).execute()) {
            //4.响应内容
            ResponseBody body = response.body();
            String str;
            if (body != null){
                str = body.string();
                return JSON.parseObject(str, GiteeUser.class);
            }
            return new GiteeUser();
        } catch (IOException e) {
            log.error("获取token失败:[{}]", e.getMessage());
            throw new CustomizeException(ErrorCodeEnum.GET_USER_INFO_FAILED);
        }
    }
}
