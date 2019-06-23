package com.xlx.majiang.controller;

import com.xlx.majiang.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

/**
 * question
 *
 * @author xielx on 2019/6/24
 */
@Controller
public class QuestionController {

  @Resource
  private QuestionService questionService;

  @GetMapping("/question/{id}")
  public  String question(@PathVariable("id") Long id){

    return "";
  }

}
