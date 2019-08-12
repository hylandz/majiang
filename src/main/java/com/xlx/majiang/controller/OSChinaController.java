package com.xlx.majiang.controller;

import com.xlx.majiang.dto.AccessTokenDTO2;
import com.xlx.majiang.dto.GiteeUser;
import com.xlx.majiang.model.User;
import com.xlx.majiang.provider.GiteeProvider;
import com.xlx.majiang.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
