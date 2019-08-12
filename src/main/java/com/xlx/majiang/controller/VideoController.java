package com.xlx.majiang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 视频
 *
 * @author xielx on 2019/8/12
 */
@Controller
public class VideosController {

	@GetMapping("/videos")
	public String videos(){
		return "video";
	}
}
