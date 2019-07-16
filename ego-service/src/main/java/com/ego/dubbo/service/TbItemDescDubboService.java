package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

/**
 * 商品描述dubbo-service-provider
 * @author 王以诚
 *
 */
public interface TbItemDescDubboService {

	/**
	 * 商品描述新增
	 * @param itemDesc
	 * @return
	 */
	int insDesc(TbItemDesc itemDesc);
	
	/**
	 * 根据商品id查询商品描述对象
	 * @param itemId
	 * @return
	 */
	TbItemDesc selByItemid(long itemId);
	
}
