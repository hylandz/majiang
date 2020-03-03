package com.xlx.majiang.controller;

import com.xlx.majiang.common.constant.Constants;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义异常捕获
 *
 * @author xielx on 2019/6/24
 */
@Controller
@RequestMapping("${server.error.path:$*{error.path:/error}")
public class CustomizeErrorController implements ErrorController {

  @Override
  public String getErrorPath() {
    return "error/error";
  }

  /**
   * 捕获异常:
   * 4xx
   * 5xx
   * @param request re
   * @return mav
   */
  public ModelAndView errorHTML(HttpServletRequest request){
    ModelAndView modelAndView = new ModelAndView("error/error");
    HttpStatus httpStatus = getStatus(request);

    // 4xx
    if (httpStatus.is4xxClientError()){
      modelAndView.addObject("message","你这个请求错了吧，要不然换个姿势？");
    }

    // 5xx
    if (httpStatus.is5xxServerError()){
      modelAndView.addObject("message","服务冒烟了，要不然你稍后再试试！！！");
    }
    return modelAndView;
  }



  private HttpStatus getStatus(HttpServletRequest request){

    Integer statusCode = (Integer) request.getAttribute(Constants.STATUS_CODE);
    if (statusCode == null){
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    try{
      return HttpStatus.valueOf(statusCode);
    }catch (Exception e){
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
  }

}
