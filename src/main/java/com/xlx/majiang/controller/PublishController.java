package com.xlx.majiang.controller;

import com.xlx.majiang.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller:问题
 *
 * @author xielx on 2019/6/22
 */
@Controller
public class PublishController {

  @Autowired
  private QuestionMapper questionMapper;

  @GetMapping("/publish")
  public String publish(){
    return "publish";
  }

  @PostMapping("/publish")
  public String doPublish(){
    return "redirect:/";
  }
}
