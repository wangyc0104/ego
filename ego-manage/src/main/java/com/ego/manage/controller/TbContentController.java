package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentService;
import com.ego.pojo.TbContent;

/**
 * 页面内容信息管理控制器
 * @author 王以诚
 */
@Controller
public class TbContentController {

	@Resource
	private TbContentService tbContentServiceImpl;

	/**
	 * 显示页面内容信息
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("content/query/list")
	@ResponseBody
	public EasyUIDataGrid showContent(long categoryId, int page, int rows) {
		return tbContentServiceImpl.showContent(categoryId, page, rows);
	}

	/**
	 * 新增页面内容（点击保存时触发的控制器）
	 * @param content
	 * @return
	 */
	@RequestMapping("content/save")
	@ResponseBody
	public EgoResult save(TbContent content) {
		EgoResult er = new EgoResult();
		int index = tbContentServiceImpl.save(content);
		if (index > 0) {
			er.setStatus(200);
		}
		return er;
	}

	/**
	 * 根据id删除页面内容（可多选）
	 * @param ids
	 * @return
	 */
	@RequestMapping("content/delete")
	@ResponseBody
	public EgoResult delete(String ids) {
		EgoResult er = new EgoResult();
		String[] idStr = ids.split(","); // 根据逗号分隔传入的id参数
		TbContent tc = new TbContent();
		int index = 0;
		for (String id : idStr) {
			tc.setId(Long.parseLong(id));
			index += tbContentServiceImpl.delete(tc);
		}
		if (index == idStr.length) {
			er.setStatus(200);
		} else {
			er.setData("数据可能已经不存在！");
		}
		return er;
	}

}
