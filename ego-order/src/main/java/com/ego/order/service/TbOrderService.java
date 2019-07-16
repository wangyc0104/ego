package com.ego.order.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.order.pojo.MyOrderParam;

public interface TbOrderService {
	
	/**
	 * 确认订单信息包含的商品
	 * @param id
	 * @return
	 */
	public List<TbItemChild> showOrderCart(List<Long> idList, HttpServletRequest request);
	
	/**
	 * 创建订单
	 * @param param
	 * @return
	 */
	public EgoResult create(MyOrderParam param, HttpServletRequest request);
	
}
