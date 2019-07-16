package com.ego.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 分页管理控制器
 * @author 王以诚
 */
@Controller
public class PageController {

	@RequestMapping("/")
	public String welcome() {
		return "index";
	}

	@RequestMapping("{page}")
	public String showPage(@PathVariable(value = "page") String page) {
		return page;
	}
	
}
