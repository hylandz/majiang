package com.xlx.majiang.dto;

import lombok.Data;

/**
 * 登录
 *
 * @author xielx on 2019/8/5
 */
@Data
public class LoginDTO {

	private String username;
	private String password;
	private Boolean rememberMe;
	private String captcha;
}
