package com.ego.item.controller;

import javax.annotation.Resource;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.item.service.TbItemCatService;

/**
 * 商品种类控制器
 * @author 王以诚
 */
@Controller
public class TbItemCatController {
	
	@Resource
	private TbItemCatService tbItemCatServiceImpl;
	
	/**
	 * 返回Jsonp数据格式，里面包含所有的菜单类信息
	 * @param callback
	 * @return
	 */
	@RequestMapping("rest/itemcat/all")
	@ResponseBody
	public MappingJacksonValue showMenu(String callback) {
		MappingJacksonValue mjv = new MappingJacksonValue(tbItemCatServiceImpl.showCatMenu());
		mjv.setJsonpFunction(callback);
		return mjv;
	}
}
