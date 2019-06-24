package com.xlx.majiang.controller;

import com.xlx.majiang.cache.TagCache;
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
 * Controller:发布问题
 *
 * @author xielx on 2019/6/22
 */
@Controller
public class PublishController {

  @Autowired
  private QuestionService questionService;

  /**
   * 跳转发布页面
   * @param model mo
   * @return string
   */
  @GetMapping("/publish")
  public String publish(Model model){
    model.addAttribute("tags", TagCache.list());
    return "publish";
  }


  /**
   * 点击编辑时数据呈现
   * @param id questionId
   * @param model model
   * @return String
   */
  @GetMapping("/publish/{id}")
  public String editor(@PathVariable("id") Long id,Model model){
    QuestionDTO questionDTO = questionService.getByQuestionId(id);
    model.addAttribute("title", questionDTO.getTitle());
    model.addAttribute("description", questionDTO.getDescription());
    model.addAttribute("tag", questionDTO.getTag());
    model.addAttribute("id", questionDTO.getId());
    model.addAttribute("tags", TagCache.list());
    return "publish";
  }

  /**
   * 发起问题
   * @param title 问题标题
   * @param description 问题内容
   * @param tag 问题标签
   * @param id 问题id
   * @param request re
   * @param model mo
   * @return String
   */
  @PostMapping("/publish")
  public String doPublish(@RequestParam(name = "title",required = false) String title,
                          @RequestParam(name = "description",required = false) String description,
                          @RequestParam(name = "tag",required = false) String tag,
                          @RequestParam(name = "id",required = false) Long id,
                          HttpServletRequest request,
                          Model model){
    model.addAttribute("title", title);
    model.addAttribute("description", description);
    model.addAttribute("tag", tag);
    model.addAttribute("tags", TagCache.list());

    //校验问题发布参数
    if (title == null || title == "") {
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
    }

    //校验tag
    String invalid = TagCache.filterInvalid(tag);
    if (StringUtils.isNotBlank(invalid)){
      model.addAttribute("error","非法输入标签:" + invalid);
      return "publish";
    }

    // 取出当前用户
    User user = (User) request.getSession().getAttribute("user");
    if (user == null){
      model.addAttribute("error","用户未登录");
      return "publish";
    }

    //发布问题<======>Question的新增
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
