package com.xlx.majiang.common.tag;

import com.xlx.majiang.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * tag
 *
 * @author xielx on 2019/6/24
 */
public class TagMenu {

  /**
   * TagDTO
   * @return List
   */
  public static List<TagDTO> list(){

    List<TagDTO>  tagDTOList = new LinkedList<>();
    TagDTO programmer = new TagDTO();
    programmer.setCategoryName("开发语言");
    programmer.setTags(Arrays.asList("javascript", "PHP", "CSS", "HTML", "HTML5", "Java", "Node.js", "Python", "C++", "C", "goLang", "objective-C", "typescript", "shell", "swift", "C#", "sass", "Ruby", "Bash", "less", "Asp.Net", "lua", "Scala", "coffeeScript", "actionScript", "Rust", "erLang", "Perl"));
    tagDTOList.add(programmer);


    TagDTO framework = new TagDTO();
    framework.setCategoryName("平台框架");
    framework.setTags(Arrays.asList("laRavel", "Spring", "Express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
    tagDTOList.add(framework);


    TagDTO server = new TagDTO();
    server.setCategoryName("服务器");
    server.setTags(Arrays.asList("linux", "ngInx", "docker", "apache", "ubuntu", "centOS", "缓存 tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
    tagDTOList.add(server);

    TagDTO db = new TagDTO();
    db.setCategoryName("数据库");
    db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "noSql memCached", "sqlServer", "postGreSql", "sqLite"));
    tagDTOList.add(db);

    TagDTO tool = new TagDTO();
    tool.setCategoryName("开发工具");
    tool.setTags(Arrays.asList("git", "github", "visual-studio-code", "vim", "sublime-text", "xcode intellij-idea", "eclipse", "maven", "ide", "svn", "visual-studio", "atom emacs", "textMate", "hg"));
    tagDTOList.add(tool);
    return tagDTOList;
  }

  /**
   * 检验并获取非法的标签
   * @param tags 待校验标签
   * @return String
   */
  public static String filterInvalid(String tags) {
    String[] tagArray = StringUtils.split(tags,",");

    List<TagDTO> tagDTOList = list();
    List<String> tagList = tagDTOList.stream().flatMap(tagDTO -> tagDTO.getTags().stream()).collect(Collectors.toList());
    String invalid = Arrays.stream(tagArray).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
    return invalid;
  }

}


