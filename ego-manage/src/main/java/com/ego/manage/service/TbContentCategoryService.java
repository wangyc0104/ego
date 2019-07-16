package com.ego.manage.service;

import java.util.List;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

/**
 * 与页面内容分类有关的service
 * @author 王以诚
 *
 */
public interface TbContentCategoryService {

	/**
	 * 查询所有内容分类并转换为EasyUiTree对象
	 * @return
	 */
	List<EasyUiTree> showCategory(long id);
	
	/**
	 * 内容分类新增
	 * @param cat
	 * @return
	 */
	EgoResult create(TbContentCategory cat);
	
	/**
	 * 重命名内容分类
	 * @param cat 带id和name参数的内容分类对象
	 * @return
	 */
	EgoResult update(TbContentCategory cat);
	
	/**
	 * 删除内容分类
	 * @param cat
	 * @return
	 */
	EgoResult delete(TbContentCategory cat);
}
