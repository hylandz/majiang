package com.xlx.majiang.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xlx.majiang.dto.AccessTokenDTO2;
import com.xlx.majiang.dto.GiteeUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 码云
 *
 * @author xielx on 2019/8/4
 */
@Slf4j
public class GiteeProvider {


	/**
	 * 获取访问密钥
	 *
	 * @param accessTokenDTO obj
	 * @return str
	 */
	public static String getAccessToken(AccessTokenDTO2 accessTokenDTO) {

		//1.创建客户端
		OkHttpClient client = new OkHttpClient();

		//2.创建传参的请求体,及数据类型json
		MediaType mediaType = MediaType.get("application/json;charset=utf-8");
		RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));

		//3.POST请求
		Request request = new Request.Builder()
															.url("https://gitee.com/oauth/token")
															.post(body)
															.build();

		//4.执行
		try (Response response = client.newCall(request).execute()) {
			//5.获取响应体内容
			String str = response.body().string();
			JSONObject object = JSONObject.parseObject(str);
			//{"access_token":"99a0da14fad6c56ce73af1ffa03ec84f","token_type":"bearer","expires_in":86400,"refresh_token":"08406df58d370be6aaa9ba2810f5e10d1cf70169ed0f1a10e454cf9a349e21eb","scope":"user_info","created_at":1564944346}
			return object.getString("access_token");
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
	public static GiteeUser getUserInfo(String access_token) {

		//1.创建客户端
		OkHttpClient client = new OkHttpClient();
		//2.创建GET请求
		Request request = new Request.Builder()
															.url("https://gitee.com/api/v5/user?access_token=" + access_token)
															.addHeader("Content-Type","application/json;charset=UTF-8")
															.build();

		//3.执行请求
		try (Response response = client.newCall(request).execute()) {
			//4.响应内容
			String str = response.body().string();
			return JSON.parseObject(str, GiteeUser.class);
		} catch (IOException e) {
			log.error("获取token失败:[{}]", e.getMessage());
			throw new ClassCastException(e.getMessage());
		}

	}


	/**
	 * 刷新token
	 * @param refreshToken fresh
	 * @return .
	 */
	public String getRefeshToken(String refreshToken){

		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.get("application/json;charset=utf-8");
		Map<String,String> param = new HashMap<>();
		//uri参数
		param.put("grantType","refresh_token");
		param.put("refreshToken",refreshToken);

		RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(param));
		//POST请求
		Request request = new Request.Builder()
						.url("https://gitee.com/oauth/token")
						.post(body)
						.build();
		try(Response response = client.newCall(request).execute()){
			String content = response.body().string();
			//获取access_token
			JSONObject object = JSONObject.parseObject(content);
			String s = "access_token";
			return object.getString(s);
		} catch (IOException e) {
			log.error("获取token失败:[{}]", e.getMessage());
			throw new ClassCastException(e.getMessage());
		}


	}


}
