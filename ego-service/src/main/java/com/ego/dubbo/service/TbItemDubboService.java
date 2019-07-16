package com.ego.dubbo.service;

import java.util.List;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

/**
 * 商品查询管理dubbo-service-provider
 * @author 王以诚
 *
 */
public interface TbItemDubboService {
	
	/**
	 * 商品分页查询
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGrid show(int page,int rows);
	
	/**
	 * 根据id修改状态
	 * @param id
	 * @param status
	 * @return
	 */
	int updItemStatus(TbItem tbItem);
	
	/**
	 * 商品新增
	 * @param tbItem
	 * @return
	 */
	int insTbItem(TbItem tbItem);
	
	/**
	 * 新增商品（同时新增商品参数表和商品描述表）
	 * @param tbItem
	 * @return
	 */
	int insTbItemDesc(TbItem tbItem, TbItemDesc desc, TbItemParamItem paramItem) throws Exception;
	
	/**
	 * 查询某状态全部数据
	 * @return
	 */
	List<TbItem> selAllByStatus(byte status);
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	TbItem selById(long id);
	
}
