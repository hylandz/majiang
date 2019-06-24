package com.xlx.majiang.advice;

import com.alibaba.fastjson.JSON;
import com.xlx.majiang.dto.ResultDTO;
import com.xlx.majiang.exception.CustomizeErrorCodeEnum;
import com.xlx.majiang.exception.CustomizeException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 异常事务-通知
 *
 * @author xielx on 2019/6/24
 */
@Controller
public class CustomizeExceptionHandler {

  @ExceptionHandler(Exception.class)
  ModelAndView handler(HttpServletRequest request, HttpServletResponse response, Throwable t, Model model) {
    String contentType = request.getContentType();

    if ("application/json".equals(contentType)) {  // 返回json
      ResultDTO resultDTO;
      //异常分类
      if (t instanceof CustomizeException) {
        resultDTO = ResultDTO.errorOf((CustomizeException) t);
      } else {
        resultDTO = ResultDTO.errorOf(CustomizeErrorCodeEnum.SYS_ERROR);
      }

      //设置响应体
      try {
        response.setContentType("application/json");
        response.setStatus(200);
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.write(JSON.toJSONString(resultDTO));
      } catch (IOException e) {
        //logger.error("response.getWriter()获取对象失败:[{}]",e.getMessage());
      }

      return null;

    } else { //
      if (t instanceof CustomizeException) {
        model.addAttribute("message", t.getMessage());
      } else {
        model.addAttribute("message", CustomizeErrorCodeEnum.SYS_ERROR.getMessage());
      }
      return new ModelAndView("error");
    }

  }
}
