package com.ego.dubbo.service;

import java.util.List;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;

/**
 * 页面内容dubbo-service-provider
 * @author 王以诚
 *
 */
public interface TbContentDubboService {

	/**
	 * 分页查询[内容]
	 * @param categoryId 商品分类id
	 * @param page 页码
	 * @param rows 每页显示条数
	 * @return
	 */
	EasyUIDataGrid selContentByPage(long categoryId, int page, int rows);
	
	/**
	 * 新增[内容]
	 * @param content
	 * @return
	 */
	int insContent(TbContent content);
	
	/**
	 * 查询出最近的前n个内容
	 * @param count
	 * @param isSort
	 * @return
	 */
	List<TbContent> selByCount(int count, boolean isSort);
	
	/**
	 * 删除[内容]
	 * @param content
	 * @return
	 */
	int delContent(TbContent content);
	
}
