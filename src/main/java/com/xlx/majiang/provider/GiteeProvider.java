package com.xlx.majiang.provider;

import com.alibaba.fastjson.JSON;
import com.xlx.majiang.dto.AccessTokenDTO2;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

/**
 * 码云
 *
 * @author xielx on 2019/8/4
 */
@Slf4j
public class GiteeProvider {


	/**
	 * 获取访问密钥
	 * @param accessTokenDTO obj
	 * @return str
	 */
	public static String getAccessToken(AccessTokenDTO2 accessTokenDTO) {

		MediaType mediaType = MediaType.get("application/json");

		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
		Request request = new Request.Builder()
						.post(body)
						.url("https://gitee.com/oauth/token")
						.build();

		try(Response response = client.newCall(request).execute()){
			String str = response.body().string();
			//{"access_token":"99a0da14fad6c56ce73af1ffa03ec84f","token_type":"bearer","expires_in":86400,"refresh_token":"08406df58d370be6aaa9ba2810f5e10d1cf70169ed0f1a10e454cf9a349e21eb","scope":"user_info","created_at":1564944346}
			return str;
		}catch (IOException e){
			log.error("获取token失败:[{}]",e.getMessage());
			throw new ClassCastException(e.getMessage());
		}
	}


	/**
	 * 获取授权的用户资料
	 * @param access_token 授权码
	 * @return
	 */
	public Object getUserInfo(String access_token){

		//https://gitee.com/api/v5/user?access_token=99a0da14fad6c56ce73af1ffa03ec84f
		return null;
	}


}
