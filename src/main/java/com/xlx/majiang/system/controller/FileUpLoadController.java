package com.xlx.majiang.system.controller;

import com.xlx.majiang.system.dto.FileUpLoadDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 文件上传
 *
 * @author xielx on 2019/6/29
 */
@Controller
public class FileUpLoadController {

  @ResponseBody
  @RequestMapping("/file/upload")
  public FileUpLoadDTO upload(){

    FileUpLoadDTO fileUpLoadDTO = new FileUpLoadDTO();
    fileUpLoadDTO.setSuccess(1);
    fileUpLoadDTO.setMessage("ok");
    fileUpLoadDTO.setUrl("/images/wechat.png");
    return fileUpLoadDTO;
  }
}
