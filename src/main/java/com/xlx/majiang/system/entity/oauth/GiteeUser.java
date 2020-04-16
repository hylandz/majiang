package com.xlx.majiang.system.entity.oauth;

import lombok.Data;

/**
 * 码云用户信息
 *
 * @author xielx on 2019/8/5
 */
@Data
public class GiteeUser {

	private Long id;
	private String name;//昵称
	private String avatarUrl;//头像
	private String bio;//备注

}
