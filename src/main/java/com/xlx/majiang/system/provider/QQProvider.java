package com.xlx.majiang.system.provider;

import com.alibaba.fastjson.JSON;
import com.xlx.majiang.system.dto.oauth.AbstractAccessToken;
import com.xlx.majiang.system.entity.oauth.QQUser;
import com.xlx.majiang.system.enums.ErrorCodeEnum;
import com.xlx.majiang.system.exception.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * QQ工具类
 *
 * @author xielx at 2020/3/8 17:12
 */
@Slf4j
@Component
public class QQProvider {
    
    
    /**
     * step2.获取AccessToken
     * @param accessTokenDTO api封装参数
     * @return token
     */
    public String getAccessToken(AbstractAccessToken accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json;charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        // POST
        String access_token_api = "https://graph.qq.com/oauth2.0/token";
        
        Request request = new Request.Builder()
                .url(access_token_api)
                .post(body)
                .build();
        try(Response response = client.newCall(request).execute()){
            final ResponseBody responseBody = response.body();
            if (responseBody == null){
                log.error("获取响内容失败");
                throw new CustomizeException(ErrorCodeEnum.GET_ACCESS_TOKEN_FAILED);
            }
            
            String str = responseBody.string();
            log.info("响应数据:{}",str);
            // 截取必要数据,access_token,expires_in,refresh_token
            return "";
        } catch (IOException e) {
            log.error("获取token失败:[{}]",e.getMessage());
            throw new CustomizeException(ErrorCodeEnum.GET_ACCESS_TOKEN_FAILED);
        }
    
    }
    
    /**
     * step3:获取OpenId
     * @param accessToken step2中的token
     * @return openId
     */
    public String getOpenId(String accessToken){
        OkHttpClient client = new OkHttpClient();
        // GET
        String open_id_api = "https://graph.qq.com/oauth2.0/me?access_token=";
        final Request request = new Request.Builder()
                                      .url(open_id_api + accessToken)
                                      .build();
    
        try(final Response response = client.newCall(request).execute()){
            final ResponseBody body = response.body();
            if (body != null){
                final String str = body.string();
                log.info("响应数据:{}",str);
                // 截取需要的字符
                return str;
            }
            return "";
        } catch (IOException e) {
            log.error("获取获取OpenId异常:{}",e.getMessage());
            throw new CustomizeException(e.getMessage());
        }
    }
    
    /**
     * step4:获取QQ用户信息
     * @param accessToken step2的token
     * @param appId QQ开放平台 https://open.tencent.com/
     * @param openId step3的openId
     * @return QQ用户信息
     */
    public QQUser getUserInfo(String accessToken, String appId, String openId) {
    
        String url = "https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s";
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                                      .addHeader("content-type", "application/json")
                                      .url(String.format(url, accessToken, appId, openId))
                                      .build();
        try(final Response response = client.newCall(request).execute()){
            final ResponseBody body = response.body();
            if (response.isSuccessful() && body!= null ){
                final String result = body.string();
                QQUser qqUser = JSON.parseObject(result, QQUser.class);
                log.info("QQ用户信息:{}",JSON.toJSONString(qqUser));
                return qqUser;
            }
            
        } catch (IOException e) {
            log.info("响应失败:{}",e.getMessage());
            throw new CustomizeException(ErrorCodeEnum.GET_USER_INFO_FAILED);
        }
    
        return new QQUser();
    }
}
