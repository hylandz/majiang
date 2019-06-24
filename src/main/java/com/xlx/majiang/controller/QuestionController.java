package com.xlx.majiang.controller;

import com.xlx.majiang.dto.CommentDTO;
import com.xlx.majiang.dto.QuestionDTO;
import com.xlx.majiang.enums.CommentTypeEnum;
import com.xlx.majiang.service.CommentService;
import com.xlx.majiang.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

/**
 * question
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
   * 查看问题
   * @param id questionId
   * @param model view
   * @return String
   */
  @GetMapping("/question/{id}")
  public  String question(@PathVariable("id") Long id, Model model){
    QuestionDTO questionDTO = questionService.getByQuestionId(id);
    List<QuestionDTO> questionDTOList = questionService.selectRelated(questionDTO);
    List<CommentDTO> commentDTOList = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

    //累加阅读量
    questionService.incView(id);
    model.addAttribute("question",questionDTO);
    model.addAttribute("relatedQuestions", questionDTOList);
    model.addAttribute("comments",commentDTOList);

    return "question";
  }

}
