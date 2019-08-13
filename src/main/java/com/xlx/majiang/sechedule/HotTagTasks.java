package com.xlx.majiang.sechedule;

import com.xlx.majiang.tag.HotTag;
import com.xlx.majiang.dao.QuestionMapper;
import com.xlx.majiang.model.Question;
import com.xlx.majiang.model.QuestionExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * springboot的定时任务
 *
 * @author xielx on 2019/8/7
 */
@Component
public class HotTagTasks {

	private static final Logger log = LoggerFactory.getLogger(HotTagTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Resource
	private QuestionMapper questionMapper;

	@Resource
	private HotTag hotTagCache;
	/**
	 * 上一次开始执行时间点之后5秒再执行
	 */
	@Scheduled(fixedRate = 3 * 60 * 60 * 1000)
	public void hotTagsSchedule(){
		log.info("hotTagSchedule stop: {}",dateFormat.format(new Date()));

		Map<String,Integer> priorityMap = new HashMap<>();
		int offset = 0;
		int size = 20;
		List<Question> questionList = new ArrayList<>();


		while (offset == 0 || size == questionList.size()){
			 questionList = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
			for (Question question : questionList){
				String[] tagArray = StringUtils.split(question.getTag(),",");
				for (String tag : tagArray){
					Integer priority = priorityMap.get(tag);
					if (priority != null){
						priorityMap.put(tag,5 + priority + question.getCommentCount());
					}else {
						priorityMap.put(tag,5 + question.getCommentCount());
					}
				}
			}
			offset+= size;
		}
		hotTagCache.updateHotTag(priorityMap);
		log.info("hotTagSchedule stop: {}", dateFormat.format(new Date()));

	}
}
