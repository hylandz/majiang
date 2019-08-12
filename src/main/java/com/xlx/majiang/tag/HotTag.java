package com.xlx.majiang.common.tag;

import com.xlx.majiang.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 热门标签
 *
 * @author xielx on 2019/8/12
 */
@Data
@Component
public class HotTag {

	private List<String> 	hotTagDTOList = new ArrayList<>();

	/**
	 * 随时更新标签的热度
	 * @param tags 标签
	 */
	public void updateHotTag(Map<String,Integer> tags){

		int max = 10;
		//定义一个容量为10优先级对列
		PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(max);
		//判断标签的数量队列是否容纳下,数量大需要剔除热度小的空间
		tags.forEach((tagName,priority)->{
			HotTagDTO hotTagDTO = new HotTagDTO(tagName,priority);
			if (priorityQueue.size() < max){
				priorityQueue.add(hotTagDTO);
			}else {
				//替换优先级低的
				HotTagDTO mini = priorityQueue.peek();
				if (hotTagDTO.compareTo(mini) > 0){
					priorityQueue.poll();
					priorityQueue.add(hotTagDTO);
				}
			}
		});

		/**************上面得到一个排列好的优先级队列*****************/

		//取出队列里的标签名称
		List<String> tagList = new ArrayList<>();
		HotTagDTO poll = priorityQueue.poll();
		while (poll != null){
			tagList.add(0,poll.getTagName());
			poll = priorityQueue.poll();
		}

		hotTagDTOList=tagList;
	}
}
