package com.xlx.majiang.controller;

import com.xlx.majiang.common.cache.Constants;
import com.xlx.majiang.dto.CommentCreateDTO;
import com.xlx.majiang.dto.CommentDTO;
import com.xlx.majiang.dto.ResultDTO;
import com.xlx.majiang.enums.CommentTypeEnum;
import com.xlx.majiang.exception.CustomizeErrorCodeEnum;
import com.xlx.majiang.model.Comment;
import com.xlx.majiang.model.User;
import com.xlx.majiang.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 评论
 *
 * @author xielx on 2019/6/24
 */
@Controller
public class CommentController {

  @Resource
  private CommentService commentService;


  /**
   * 回复评论/问题
   * `@RequestBody`注解:当前端contentType:application/json使用
   * @param commentCreateDTO dto
   * @param request re
   * @return dto
   */
  @ResponseBody
  @RequestMapping(value = "/comment",method = RequestMethod.POST)
  public ResultDTO comment(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request){


    User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);
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
    comment.setLikeCount(0);
    comment.setCommentCount(0);

    commentService.insert(comment,user);
    return  ResultDTO.okOf();
  }


  /**
   * 展开二级列表时,显示二级列表的评论数据
   * @param id 问题回答的id
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
  public ResultDTO<List<CommentDTO>> comments(@PathVariable("id") Long id){

    List<CommentDTO> commentDTOList = commentService.listCommentByIdType(id, CommentTypeEnum.COMMENT);
    return ResultDTO.oKOf(commentDTOList);
  }
}
