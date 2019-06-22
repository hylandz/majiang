package com.xlx.majiang.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class IndexController {

  /*
   *
   * @param name
   * @return
   */
  @GetMapping("/")
  public String index(){
    return "navigation";
  }

}
