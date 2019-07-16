package com.ego.dubbo.service;

import com.ego.pojo.TbItemParamItem;

/**
 * 商品参数对象dubbo-service-provider
 * @author 王以诚
 */
public interface TbItemParamItemDubboService {
	
	/**
	 * 根据商品id查询商品规格参数
	 * @param itemId
	 * @return
	 */
	TbItemParamItem selByItemId(long itemId);
	
}
