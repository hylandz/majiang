package com.xlx.majiang.controller;

import com.xlx.majiang.dto.CommentCreateDTO;
import com.xlx.majiang.dto.CommentDTO;
import com.xlx.majiang.dto.ResultDTO;
import com.xlx.majiang.exception.CustomizeErrorCodeEnum;
import com.xlx.majiang.model.Comment;
import com.xlx.majiang.model.User;
import com.xlx.majiang.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 评论
 *
 * @author xielx on 2019/6/24
 */
@Controller
public class CommentController {

  @Resource
  private CommentService commentService;


  @ResponseBody
  @RequestMapping(value = "/comment",method = RequestMethod.POST)
  public ResultDTO comment(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request){

    User user = (User) request.getSession().getAttribute("user");
    if (user == null){
      return ResultDTO.errorOf(CustomizeErrorCodeEnum.NOT_LOGIN);
    }

    if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
      return ResultDTO.errorOf(CustomizeErrorCodeEnum.CONTENTS_IS_EMPTY);
    }

    Comment comment = new Comment();

    comment.setParentId(commentCreateDTO.getParentId());
    comment.setContent(commentCreateDTO.getContent());
    comment.setType(commentCreateDTO.getType());
    comment.setGmtModified(System.currentTimeMillis());
    comment.setGmtCreate(System.currentTimeMillis());
    comment.setCommentator(user.getId());
    //comment.setLikeCount();

    return  ResultDTO.okOf();
  }
}
