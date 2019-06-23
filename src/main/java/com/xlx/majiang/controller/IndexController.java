package com.xlx.majiang.controller;

import com.xlx.majiang.dto.PaginationDTO;
import com.xlx.majiang.mapper.UserMapper;
import com.xlx.majiang.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {

  @Autowired
  private QuestionService questionService;


  @GetMapping("/")
  public String index(@RequestParam(name = "page", defaultValue = "1") Integer page,
                      @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                      Model model) {
    PaginationDTO paginationDTO = questionService.list(page, size);
    System.out.println("agination=" + paginationDTO);
    model.addAttribute("pagination", paginationDTO);

    return "index";
  }



}
