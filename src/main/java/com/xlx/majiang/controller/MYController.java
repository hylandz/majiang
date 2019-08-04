package com.xlx.majiang.controller;

import com.xlx.majiang.dto.AccessTokenDTO2;
import com.xlx.majiang.provider.GiteeProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * 码云认证
 *
 * @author xielx on 2019/8/5
 */
@Slf4j
@Controller
public class MYController {

	@Value("${gitee.client.id}")
	private String clientId;
	@Value("${gitee.client.secret}")
	private String clientSecret;
	@Value("${gitee.redirect.uri}")
	private String redirectUri;


	@GetMapping("/callbackToMY")
	public String callbackToMY(@RequestParam(name = "code") String code,
														 HttpServletResponse response) {
		AccessTokenDTO2 tokenDTO = new AccessTokenDTO2("authorization_code",code,clientId,redirectUri,clientSecret);

		String token = GiteeProvider.getAccessToken(tokenDTO);
    log.info("token:[{}]",token);


		return "";
	}
}
