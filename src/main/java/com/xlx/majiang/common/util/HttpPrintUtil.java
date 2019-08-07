package com.xlx.majiang.common.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author xielx on 2019/8/5
 */
@Slf4j
public class HttpPrintUtil {


	/**
	 * 输出
	 * @param response resp
	 * @param object obj
	 */
	public static void httpOut(HttpServletResponse response,Object object) {
		response.setContentType("text/html;charset=utf-8");
		response.setStatus(200);
		response.setCharacterEncoding("utf-8");
		try {
			PrintWriter printWriter = response.getWriter();
			printWriter.println(JSON.toJSONString(object));
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			log.error("response.getWriter()获取对象失败:[{}]", e.getMessage());
			throw new ClassCastException(e.getMessage());
		}
	}
}
