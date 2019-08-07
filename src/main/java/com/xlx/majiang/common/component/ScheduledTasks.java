package com.xlx.majiang.common.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * springboot的定时任务
 *
 * @author xielx on 2019/8/7
 */
@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormate = new SimpleDateFormat("HH:mm:ss");

	/**
	 * 上一次开始执行时间点之后5秒再执行
	 */
	@Scheduled(fixedRate = 5000)
	public void reportedCurrentTime(){
		log.info("The time is now {}",dateFormate.format(new Date()));
	}
}
