package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemParamService;
import com.ego.pojo.TbItemParam;

/**
 * 商品规格参数管理控制器
 * @author 王以诚
 */
@Controller
public class TbItemParamController {

	@Resource
	private TbItemParamService tbItemParamServiceImpl;

	/**
	 * 规格参数的分页显示
	 * @param page
	 * @param rows
	 * @return 规格参数的分页显示
	 */
	@RequestMapping("item/param/list")
	@ResponseBody
	public EasyUIDataGrid showPage(int page, int rows) {
		EasyUIDataGrid datagrid = tbItemParamServiceImpl.showPage(page, rows);
		return datagrid;
	}

	/**
	 * 删除商品规格参数
	 * @param ids
	 * @return
	 */
	@RequestMapping("item/param/delete")
	@ResponseBody
	public EgoResult delete(String ids) {
		EgoResult er = new EgoResult();
		try {
			int index = tbItemParamServiceImpl.delete(ids);
			if (index == 1) {
				er.setStatus(200);
			}
		} catch (Exception e) {
			e.printStackTrace();
			er.setData(e.getMessage());
		}
		return er;
	}
	
	/**
	 * 点击商品类目控制显示添加分组按钮 <br>
	 * 判断类目是否已经添加模板 <br>
	 * @param catId
	 * @return
	 */
	@RequestMapping("item/param/query/itemcatid/{catId}")
	@ResponseBody
	public EgoResult show(@PathVariable long catId) {
		return tbItemParamServiceImpl.showParam(catId);
	}

	/**
	 * 商品类目的新增
	 * @param param
	 * @param catId
	 * @return
	 */
	@RequestMapping("item/param/save/{catId}")
	@ResponseBody
	public EgoResult save(TbItemParam param, @PathVariable long catId) {
		param.setItemCatId(catId);
		return tbItemParamServiceImpl.save(param);
	}
	
}
