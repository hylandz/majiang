package com.xlx.majiang.demo01;

import com.xlx.majiang.entity.User;
import com.xlx.majiang.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * UserService
 *
 * @author xielx on 2019/8/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {


	@Resource
	private UserService userService;

	@Test
	public void testUserService(){
		User user = userService.findUserByPwd("41381772");
		System.out.println(user);
	}
}
