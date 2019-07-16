package com.ego.portal.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ego.portal.service.TbContentService;

/**
 * 门户页面内容控制器
 * @author 王以诚
 */
@Controller
public class TbContentController {

	@Resource
	private TbContentService tbContentServiceImpl;
	
	/**
	 * 前台广告轮播展示
	 * @param model
	 * @return
	 */
	@RequestMapping("showBigPic")
	public String showBigPic(Model model) {
		model.addAttribute("ad1", tbContentServiceImpl.showBigPic());
		return "index";
	}
	
}
