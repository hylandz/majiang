package com.xlx.majiang.controller;

import com.xlx.majiang.tag.HotTag;
import com.xlx.majiang.dto.PaginationDTO;
import com.xlx.majiang.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 首页
 */
@Controller
@Slf4j
public class IndexController {

  @Autowired
  private QuestionService questionService;

  @Autowired
  private HotTag hotTagCache;
  /**
   * 首页:
   * 未登录可以查看问题
   * @param page 当前页
   * @param size 页面容量
   * @param model model
   * @return String
   */
  @GetMapping("/")
  public String index(@RequestParam(name = "page", defaultValue = "1") Integer page,
                      @RequestParam(name = "size", defaultValue = "5") Integer size,
                      @RequestParam(name = "search",required = false) String search,
                      @RequestParam(name = "tag",required = false) String tag,
                      Model model) {

    log.info("查询参数:[{}]",search);
    List<String> tagList =  hotTagCache.getHotTagDTOList();
    log.info("热门标签:[{}]",tagList);
    PaginationDTO paginationDTO = questionService.list(page, size,search,tag);
    PaginationDTO topic = questionService.getHotQuestion();
    model.addAttribute("pagination", paginationDTO);
    model.addAttribute("topic", topic);
    model.addAttribute("search", search);
    model.addAttribute("tags", tagList);

    return "index";
  }




}
