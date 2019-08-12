package com.xlx.majiang.controller;

import com.xlx.majiang.dto.CommentDTO;
import com.xlx.majiang.dto.QuestionDTO;
import com.xlx.majiang.common.enums.CommentTypeEnum;
import com.xlx.majiang.service.CommentService;
import com.xlx.majiang.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

/**
 * question: 问题
 *
 * @author xielx on 2019/6/24
 */
@Controller
public class QuestionController {

  @Resource
  private QuestionService questionService;
  @Resource
  private CommentService commentService;

  /**
   * 点击问题标题显示如下:
   * 1.获取该问题的内容
   * 2.获取与该问题tag标签类似的所有QuestionDTO集(页面右边相关问题功能)
   * 3.获取对该问题的所有回复,不是对问题回答的评论
   * @param questionId 问题id
   * @param model view
   * @return String
   */
  @GetMapping("/question/{id}")
  public  String question(@PathVariable("id") Long questionId, Model model){
    QuestionDTO questionDTO = questionService.getQuestionById(questionId);
    List<QuestionDTO> questionDTOList = questionService.selectRelated(questionDTO);
    List<CommentDTO> commentDTOList = commentService.listCommentByIdType(questionId, CommentTypeEnum.QUESTION);

    //浏览问题一次累加阅读量
    questionService.incView(questionId);
    model.addAttribute("question",questionDTO);
    model.addAttribute("relatedQuestions", questionDTOList);
    model.addAttribute("comments",commentDTOList);

    return "question";
  }

}
