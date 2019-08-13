package com.xlx.majiang.dto;

import lombok.Data;

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


}
