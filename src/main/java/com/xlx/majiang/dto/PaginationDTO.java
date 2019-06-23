package com.xlx.majiang.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.LinkedList;
import java.util.List;

/**
 * 分页
 *
 * @author xielx on 2019/6/23
 */
@Data
public class PaginationDTO <T>{

  private boolean showPrevious; // 展示上一页
  private boolean showFirstPage; // 展示第一页
  private boolean showNextPage; // 展示下一页
  private boolean showEndPage; // 展示最后一页

  private Integer page;//当前页
  private Integer totalPage;//总页数

  private List<T> data; // 存储分页后的数据
  private List<Integer> pages = new LinkedList<>(); //



  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
  }

  /**
   * 设置 上页,当前页,下页的顺序
   * @param page
   * @param totalPage
   */
  public void setPagination(Integer page,Integer totalPage){
    this.page = page;
    this.totalPage = totalPage;

    pages.add(page);//page
    for (int i = 1; i <= 3; i++) { // previous page  next
      if(page - i > 0){
        pages.add(0,page - i);//previous
      }

      if (page + i <= totalPage){
        pages.add(page + i); // next
      }

      //展示上一页
      this.showPrevious = page != 1;

      //展示下一页
      this.showNextPage = page != totalPage;

      //展示第一页
      this.showFirstPage = !pages.contains(1);

      //展示最后一页
      this.showEndPage = !pages.contains(totalPage);



    }

  }
}
