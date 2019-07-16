package com.ego.manage.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.manage.service.TbItemCatService;

/**
 * 商品类别管理控制器
 * @author 王以诚
 */
@Controller
public class TbItemCatController {

	@Resource
	private TbItemCatService tbItemCatServiceImpl;

	/**
	 * 展示商品类别
	 * @param id 商品类别id
	 * @return EasyUiTree信息
	 */
	@RequestMapping("item/cat/list")
	@ResponseBody
	public List<EasyUiTree> showCat(@RequestParam(defaultValue = "0") long id) {
		return tbItemCatServiceImpl.show(id);
	}

}
