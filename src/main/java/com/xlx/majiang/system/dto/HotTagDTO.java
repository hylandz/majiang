package com.xlx.majiang.system.dto;

import lombok.Data;

/**
 * 热门标签dto
 *
 * @author xielx on 2019/8/12
 */
@Data
public class HotTagDTO implements Comparable {

	//标签名
	private String tagName;

	// 优先级
	private Integer priority;

	public HotTagDTO(String tagName, Integer priority) {
		this.tagName = tagName;
		this.priority = priority;
	}

	@Override
	public int compareTo(Object o) {
		return this.priority - ((HotTagDTO) o).priority;
	}
}
