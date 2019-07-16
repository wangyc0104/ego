package com.ego.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 门户页面控制器
 * @author 王以诚
 */
@Controller
public class PageController {
	
	/**
	 * 进入主页后，会转发至“显示大广告”控制器
	 * @return
	 */
	@RequestMapping("/")
	public String welcome() {
		System.out.println("负载均衡执行测试");
		return "forward:/showBigPic";
	}
	
}
