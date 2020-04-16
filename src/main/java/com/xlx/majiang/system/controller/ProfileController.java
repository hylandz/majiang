package com.xlx.majiang.system.controller;

import com.xlx.majiang.common.constant.Constants;
import com.xlx.majiang.system.dto.PaginationDTO;
import com.xlx.majiang.system.entity.Question;
import com.xlx.majiang.system.entity.User;
import com.xlx.majiang.system.service.NotificationService;
import com.xlx.majiang.system.service.QuestionService;
import com.xlx.majiang.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 通知模块
 *
 * @author xielx on 2019/6/24
 */
@Controller
public class ProfileController {
    
    
    @Resource
    private QuestionService questionService;
    
    @Resource
    private NotificationService notificationService;
    
    @Autowired
    private UserService userService;
    
    /**
     * @param page    currPage
     * @param size    pageSize
     * @param action  param
     * @param request re
     * @param model   mo
     * @return String
     */
    @GetMapping("/profile/{action}")
    public String profile(@RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                          @PathVariable(name = "action") String action,
                          HttpServletRequest request,
                          Model model) {
        
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        if (user == null) {
            return "redirect:/";
        }
        
        //分页获取我的问题
        if ("questions".equals(action)) {
            PaginationDTO<Question> paginationDTO = questionService.list(user.getId(), page, size);
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            model.addAttribute("pagination", paginationDTO);
        } else if ("replies".equals(action)) {
            //分页获取我的回复
            PaginationDTO<Question> paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("pagination", paginationDTO);
        }
        
        return "notification";
    }
    
    /**
     * 显示用户数据
     *
     * @return html
     */
    @GetMapping("/profile")
    public String showUserProfile(HttpServletRequest request,Model model) {
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
        if (user == null){
            model.addAttribute("msg","请登录");
        }else {
            model.addAttribute("user",user);
        }
        return "profile";
    }
}
