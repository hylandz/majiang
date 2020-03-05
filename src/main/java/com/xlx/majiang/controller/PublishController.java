package com.xlx.majiang.controller;

import com.xlx.majiang.common.constant.Constants;
import com.xlx.majiang.tag.TagMenu;
import com.xlx.majiang.dto.QuestionDTO;
import com.xlx.majiang.model.Question;
import com.xlx.majiang.model.User;
import com.xlx.majiang.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Question业务处理:
 * 1.问题发布(就是新增问题)
 * 2.问题的修改
 * @author xielx on 2019/6/22
 */
@Controller
public class PublishController {

  @Autowired
  private QuestionService questionService;

  /**
   * 点击发布按钮,跳转发布页面
   * @param model mo
   * @return string
   */
  @GetMapping("/publish")
  public String publish(Model model){
    model.addAttribute("tags", TagMenu.list());
    return "publish";
  }


  /**
   * 点击问题编辑时数据呈现
   * @param id questionId
   * @param model model
   * @return String
   */
  @GetMapping("/publish/{id}")
  public String editor(@PathVariable("id") Long id,Model model){
    QuestionDTO questionDTO = questionService.getQuestionById(id);
    model.addAttribute("title", questionDTO.getTitle());
    model.addAttribute("description", questionDTO.getDescription());
    model.addAttribute("tag", questionDTO.getTag());
    model.addAttribute("id", questionDTO.getId());
    model.addAttribute("edit", "编辑");//改变按钮名称
    model.addAttribute("tags", TagMenu.list());
    return "publish";
  }

  /**
   * 问题新增与修改:id判断
   * @param title 问题标题
   * @param description 问题内容
   * @param tag 问题标签
   * @param id 问题id
   * @param request re
   * @param model mo
   * @return String
   */
  @PostMapping("/publish")
  public String doPublish(@RequestParam(name = "title",required = false)  String title,
                          @RequestParam(name = "description",required = false) String description,
                          @RequestParam(name = "tag",required = false) String tag,
                          @RequestParam(name = "id",required = false) Long id,
                          HttpServletRequest request,
                          Model model){

     /* 如果能够提示错误信息后,跳转登录成功,再保存也行,
      * 或者这个页面未登录本就不能跳转过来直接跳转首页或登录界面)
      */

    //未登录状态,发布问题仍然数据显示,
    model.addAttribute("title", title);
    model.addAttribute("description", description);
    model.addAttribute("tag", tag);
    model.addAttribute("tags", TagMenu.list());

    //校验问题发布参数
    /*if (title == null || title == "") {
      model.addAttribute("error", "标题不能为空");
      return "publish";
    }
    if (description == null || description == "") {
      model.addAttribute("error", "问题补充不能为空");
      return "publish";
    }
    if (tag == null || tag == "") {
      model.addAttribute("error", "标签不能为空");
      return "publish";
    }*/

    //校验tag
    String invalid = TagMenu.filterInvalid(tag);
    if (StringUtils.isNotBlank(invalid)){
      model.addAttribute("error","非法输入标签:" + invalid);
      return "publish";
    }

    // 取出当前用户
    User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
    if (user == null){
      model.addAttribute("error","用户未登录");
      return "publish";
    }

    //Question的新增
    Question question = new Question();
    question.setTitle(title);
    question.setDescription(description);
    question.setTag(tag);
    question.setCreator(user.getId());
    question.setId(id);
    questionService.createOrUpdate(question);
    return "redirect:/";
  }
}
