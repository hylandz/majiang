package com.xlx.majiang.controller;

import com.xlx.majiang.dto.AccessTokenDTO2;
import com.xlx.majiang.dto.GiteeUser;
import com.xlx.majiang.exception.CustomizeException;
import com.xlx.majiang.model.User;
import com.xlx.majiang.provider.GiteeProvider;
import com.xlx.majiang.service.UserService;
import com.xlx.majiang.util.HttpPrintUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * 码云认证
 *
 * @author xielx on 2019/8/5
 */
@Slf4j
@Controller
public class OSChinaController {

	@Value("${gitee.client.id}")
	private String clientId;
	@Value("${gitee.client.secret}")
	private String clientSecret;
	@Value("${gitee.redirect.uri}")
	private String redirectUri;

	@Resource
	private UserService userService;


	/**
	 * 获取code
	 */
	@GetMapping("/auth/gitee")
	public String authorizeByGitee(HttpServletResponse httpResponse){

		String url = "https://gitee.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=%s";
		//1.创建客户端
		OkHttpClient client = new OkHttpClient();
		//3.创建GET请求
		Request request = new Request.Builder()
						.url(String.format(url,clientId,redirectUri,"code"))
						.build();
		//4.执行
		try(Response response = client.newCall(request).execute()){
			ResponseBody responseBody = response.body();
			if (responseBody == null){
				log.error("获取响应体失败~~~~~~");
				throw new CustomizeException("获取响应体失败[{}]");
			}
			String content = responseBody.string();
			MediaType contentType = responseBody.contentType();

			if (response.isSuccessful() && contentType != null){
				if (contentType.toString().contains("application/json")){
					return "redirect:/callbackToMY";
				}else if (contentType.toString().contains("text/html")){
					HttpPrintUtil.httpOut(httpResponse,content);
				}else {
					return "redirect:	/login";
				}
			}
		} catch (IOException e) {
			log.error("获取token失败:[{}]", e.getMessage());
			throw new ClassCastException(e.getMessage());
		}

		return null;
	}


	/**
	 * 回调,先获取密钥,再获取用户信息
	 * @param code code
	 * @param response .
	 * @return .
	 */
	@GetMapping("/callbackToMY")
	public String callbackToMY(@RequestParam(name = "code") String code, HttpServletResponse response) {
		AccessTokenDTO2 tokenDTO = new AccessTokenDTO2("authorization_code",code,clientId,redirectUri,clientSecret);

		String accessToken = GiteeProvider.getAccessToken(tokenDTO);
    log.info("token:[{}]",accessToken);

		GiteeUser giteeUser = GiteeProvider.getUserInfo(accessToken);
		if (giteeUser != null && giteeUser.getId() != null){
			String token = UUID.randomUUID().toString();
			User user = new User(String.valueOf(giteeUser.getId()),giteeUser.getName(),token,System.currentTimeMillis(),System.currentTimeMillis(),giteeUser.getBio(),giteeUser.getAvatarUrl());
			//BeanUtils.copyProperties(giteeUser,user);

			userService.createOrUpdate(user);

			response.addCookie(new Cookie("token",token));
			return "redirect:/";
		}else {

			return "redirect:/login";
		}
	}
}
