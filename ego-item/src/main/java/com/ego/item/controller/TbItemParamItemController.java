package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.item.service.TbItemParamItemService;

/**
 * 【商品参数信息模板】控制器
 * @author 王以诚
 */
@Controller
public class TbItemParamItemController {
	
	@Resource
	private TbItemParamItemService tbItemParamItemServiceImpl;
	
	/**
	 * 根据商品的id来获取【商品参数信息模板】
	 * @param id
	 * @return 包含了【商品参数信息模板】的Html格式字符串
	 */
	@RequestMapping(value = "item/param/{id}.html", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String param(@PathVariable long id) {
		return tbItemParamItemServiceImpl.showParam(id);
	}
	
}
