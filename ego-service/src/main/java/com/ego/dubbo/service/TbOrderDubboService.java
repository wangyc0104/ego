package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

/**
 * 商品订单dubbo-service-provider
 * @author 王以诚
 *
 */
public interface TbOrderDubboService {

	/**
	 * 创建订单
	 * @param order
	 * @param list
	 * @param shipping
	 * @return
	 */
	int insOrder(TbOrder order, List<TbOrderItem> list, TbOrderShipping shipping) throws Exception;

}
