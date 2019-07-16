package com.ego.manage.service;

import java.util.List;

import com.ego.commons.pojo.EasyUiTree;

/**
 * 商品分类有关的service
 * @author 王以诚
 *
 */
public interface TbItemCatService {
	
	/**
	 * 根据父菜单id显示所有的子菜单
	 * @param pid 父菜单id
	 * @return 所有的子菜单对象
	 */
	List<EasyUiTree> show(long pid);
	
}
