package com.ego.manage.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;

/**
 * 页面内容分类管理控制器
 * @author 王以诚
 */
@Controller
public class TbContentCategoryController {

	@Resource
	private TbContentCategoryService tbContentCategoryServiceImpl;
	
	/**
	 * 将页面内容分类管理列举出来
	 * @param id
	 * @return
	 */
	@RequestMapping("content/category/list")
	@ResponseBody
	public List<EasyUiTree> showCategory(@RequestParam(defaultValue="0") long id) {
		return tbContentCategoryServiceImpl.showCategory(id);
	}
	
	/**
	 * 新增页面内容分类（右键新增提交后触发的控制器）
	 * @param cat
	 * @return
	 */
	@RequestMapping("content/category/create")
	@ResponseBody
	public EgoResult create(TbContentCategory cat) {
		return tbContentCategoryServiceImpl.create(cat);
	}
	
	/**
	 * 重命名页面内容分类
	 * @param cat
	 * @return
	 */
	@RequestMapping("content/category/update")
	@ResponseBody
	public EgoResult update(TbContentCategory cat) {
		return tbContentCategoryServiceImpl.update(cat);
	}
	
	/**
	 * 删除页面内容分类
	 * @param cat
	 * @return
	 */
	@RequestMapping("content/category/delete")
	@ResponseBody
	public EgoResult delete(TbContentCategory cat) {
		return tbContentCategoryServiceImpl.delete(cat);
	}
}
