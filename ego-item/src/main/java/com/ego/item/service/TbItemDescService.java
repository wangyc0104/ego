package com.ego.item.service;

public interface TbItemDescService {

	/**
	 * 根据商品id显示商品描述
	 * @param itemId
	 * @return 商品描述字符串
	 */
	public String showDesc(long itemId);
	
}
