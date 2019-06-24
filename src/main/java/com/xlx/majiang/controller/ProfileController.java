package com.xlx.majiang.controller;

import com.xlx.majiang.dto.PaginationDTO;
import com.xlx.majiang.model.Question;
import com.xlx.majiang.model.User;
import com.xlx.majiang.service.NotificationService;
import com.xlx.majiang.service.QuestionService;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * ProfileController
 *
 * @author xielx on 2019/6/24
 */
public class ProfileController {


  @Resource
  private QuestionService questionService;

  @Resource
  private NotificationService notificationService;


  /**
   *
   * @param page currPage
   * @param size pageSize
   * @param action param
   * @param request re
   * @param model mo
   * @return String
   */
  @GetMapping("/profile/{action}")
  public String profile(@RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",required = false,defaultValue = "5") Integer size,
                        @PathVariable("action") String action,
                        HttpServletRequest request,
                        Model model){

    User user = (User) request.getSession().getAttribute("user");
    if (user == null){
      return "redirect:/";
    }

    //????
    if ("question".equals(action)){
      PaginationDTO<Question> paginationDTO = questionService.list(user.getId(),page,size);
      model.addAttribute("section","questions");
      model.addAttribute("sectionName","我的提问");
      model.addAttribute("pagination",paginationDTO);
    }else if ("replies".equals(action)){
      PaginationDTO<Question> paginationDTO = notificationService.list(user.getId(),page,size);
      model.addAttribute("section","replies");
      model.addAttribute("sectionName","最新回复");
      model.addAttribute("pagination",paginationDTO);
    }

    return "profile";

  }
}
