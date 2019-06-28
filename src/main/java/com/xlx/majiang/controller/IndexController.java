package com.xlx.majiang.controller;

import com.xlx.majiang.dto.PaginationDTO;
import com.xlx.majiang.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 首页
 */
@Controller
public class IndexController {

  @Autowired
  private QuestionService questionService;


  /**
   * 首页:
   * 未登录可以查看问题
   * @param page 当前页
   * @param size 页面容量
   * @param model model
   * @return String
   */
  @GetMapping("/")
  public String index(@RequestParam(name = "page", defaultValue = "1") Integer page,
                      @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                      Model model) {
    PaginationDTO paginationDTO = questionService.list(page, size);

    model.addAttribute("pagination", paginationDTO);

    return "index";
  }




}
