package com.xlx.majiang.common.advice;

import com.alibaba.fastjson.JSON;
import com.xlx.majiang.dto.ResultDTO;
import com.xlx.majiang.exception.CustomizeErrorCodeEnum;
import com.xlx.majiang.exception.CustomizeException;
import com.xlx.majiang.exception.ValidateCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author xielx on 2019/6/24
 */
@Slf4j
@ControllerAdvice(basePackages = "com.xlx.majiang")
public class CustomizeExceptionHandler {

  @ExceptionHandler(ValidateCodeException.class)
  @ResponseBody
  public ResultDTO handlerValidateCodeException(ValidateCodeException e){
    log.error("捕获ValidateCodeException异常:{}",e.getMessage());
    return ResultDTO.errorOf(1010,e.getMessage());
  }
  
  /**
   * Controller层出现的方法参数校验异常
   *
   * @param e MethodArgumentNotValidException
   * @return 统一返回结果
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public ResultDTO handleMethodException(MethodArgumentNotValidException e){
    BindingResult bindingResult = e.getBindingResult();
    Map<String,String> errMap = null;
    if (bindingResult.hasErrors()){
        errMap = bindingResult.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage));
        log.error("参数校验异常:{}",errMap);
    }
    return ResultDTO.errorOf(CustomizeErrorCodeEnum.PARAMS_VALIDATE_ERROR,errMap);
  }
  
  
  /**
   * Service层出现的校验异常
   *
   * @param e 约束异常
   * @return 统一结果
   */
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseBody
  public ResultDTO handleConstraintViolationException(ConstraintViolationException e) {
    Set<ConstraintViolation<?>> violationSet = e.getConstraintViolations();
    
    Map<Path, String> errMap = null;
    if (violationSet.size() > 0) {
      errMap = violationSet.stream()
                        .collect(Collectors.toMap(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage));
      log.error("ConstraintViolationException are:[{}]", errMap);
    }
    return ResultDTO.errorOf(CustomizeErrorCodeEnum.PARAMS_VALIDATE_ERROR,errMap);
  }
  
  
  @ExceptionHandler(Exception.class)
  public ModelAndView customizeHandler(HttpServletRequest request, HttpServletResponse response, Throwable t, Model model) {
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
      model.addAttribute("message", t.getMessage());
      return new ModelAndView("error/error");
    }

  }
}
