package com.xlx.majiang.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 视频
 *
 * @author xielx on 2019/8/12
 */
@Slf4j
@Controller
public class VideoController {

	@GetMapping("/video")
	public String index(){
		return "video";
	}

	@GetMapping("/video_details")
	public String video(@RequestParam(name = "aid") String aid,
											@RequestParam(name = "page",defaultValue = "1") String page,
											Model model){

		log.info("aid:[{}],page:[{}]",aid,page);
		model.addAttribute("aid",aid);
		model.addAttribute("page",page);
		return "video_details";
	}
}
