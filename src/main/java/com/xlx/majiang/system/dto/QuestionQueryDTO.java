package com.xlx.majiang.system.dto;

import lombok.Data;

import java.util.Date;

/**
 * 搜索问题
 *
 * @author xielx on 2019/8/11
 */
@Data
public class QuestionQueryDTO {

	private Integer offset;
	private Integer size;
	private String search;
	private String tag;

	/**
	 * 创建时间
	 */
	private Date time;
	
	/**
	 * 分类
	 */
	private String sort;
	

}
