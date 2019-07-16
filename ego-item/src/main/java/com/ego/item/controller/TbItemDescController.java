package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.item.service.TbItemDescService;

/**
 * 商品描述控制器
 * @author 王以诚
 */
@Controller
public class TbItemDescController {
	
	@Resource
	private TbItemDescService tbItemDescServiceImpl;
	
	/**
	 * 显示商品描述
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "item/desc/{id}.html", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String desc(@PathVariable long id) {
		return tbItemDescServiceImpl.showDesc(id);
	}
	
}
