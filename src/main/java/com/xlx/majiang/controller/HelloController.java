package com.xlx.majiang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 测试
 * @author xielx
 * @date 2019/6/21
 **/
@Controller
public class HelloController {

  /*
   *
   * @param name
   * @return
   */
  @GetMapping("/hello")
  public String index(@RequestParam(name = "username") String name, Model model){
    model.addAttribute("name",name);
    return "index";
  }

}
