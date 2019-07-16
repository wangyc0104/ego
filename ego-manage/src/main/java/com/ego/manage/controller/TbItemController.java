package com.ego.manage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;

/**
 * 商品管理控制器
 * @author 王以诚
 */
@Controller
public class TbItemController {

	@Resource
	private TbItemService tbItemServiceImpl;

	/**
	 * 显示商品列表界面
	 * @param page 页码
	 * @param rows 每页显示条目数
	 * @return
	 */
	@RequestMapping("item/list")
	@ResponseBody
	public EasyUIDataGrid show(int page, int rows) {
		return tbItemServiceImpl.show(page, rows);
	}

	/**
	 * 显示商品修改界面
	 * @return 商品修改的jsp页面
	 */
	@RequestMapping("/rest/page/item-edit")
	public String showItemEdit() {
		return "item-edit";
	}

	/**
	 * 商品上架
	 * @param ids 商品编号的字符串（以逗号分隔id）
	 * @return 修改结果（status == 200为正确，其它为错误）
	 */
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public EgoResult reshelf(String ids) {
		EgoResult egoResult = new EgoResult();
		int index = tbItemServiceImpl.update(ids, (byte) 1);
		if (index == 1) {
			egoResult.setStatus(200);
		}
		return egoResult;
	}

	/**
	 * 商品下架
	 * @param ids 商品编号的字符串（以逗号分隔id）
	 * @return 修改结果（status == 200为正确，其它为错误）
	 */
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public EgoResult instock(String ids) {
		EgoResult egoResult = new EgoResult();
		int index = tbItemServiceImpl.update(ids, (byte) 2);
		if (index == 1) {
			egoResult.setStatus(200);
		}
		return egoResult;
	}

	/**
	 * 商品删除
	 * @param ids 商品编号的字符串（以逗号分隔id）
	 * @return 修改结果（status == 200为正确，其它为错误）
	 */
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public EgoResult delete(String ids) {
		EgoResult egoResult = new EgoResult();
		int index = tbItemServiceImpl.update(ids, (byte) 3);
		if (index == 1) {
			egoResult.setStatus(200);
		}
		return egoResult;
	}

	/**
	 * 商品添加
	 * @param tbItem
	 * @param desc
	 * @param itemParams
	 * @return
	 */
	@RequestMapping("item/save")
	@ResponseBody
	public EgoResult insert(TbItem tbItem, String desc, String itemParams) {
		EgoResult er = new EgoResult();
		int index;
		try {
			index = tbItemServiceImpl.save(tbItem, desc, itemParams);
			System.out.println("com.ego.manage.controller.TbItemController.insert(TbItem, String, String) controller: index: " + index);
			if (index == 1) {
				er.setStatus(200);
			}
		} catch (Exception e) {
			er.setData(e.getMessage());
		}
		return er;
	}
	
}
