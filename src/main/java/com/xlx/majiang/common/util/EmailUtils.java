package com.xlx.majiang.common.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * 邮件工具类
 *
 * @author xielx on 2019/6/26
 */
@Slf4j
public class EmailUtils {

	/**
	 * 获取4位随机字符
	 *
	 * @return str
	 */
	public static String getRandomCode() {
		//不包括数字0,1和字母"o","i",,"l","O","I"
		String[] codeArray = {"2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
						"G", "H", "I", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a",
						"b", "c", "d", "e", "f", "g", "h", "j", "k", "m", "n", "p", "q", "r", "s", "t", "u", "v",
						"w", "x", "y", "z"};

		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			sb.append(codeArray[random.nextInt(codeArray.length)]);
		}
		return sb.toString();
	}


	/**
	 * 获取6位随机数字
	 *
	 * @return str
	 */
	public static String getRandomNumber() {
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			sb.append(r.nextInt(10));
		}
		return sb.toString();
	}


}

