package com.ego.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.TbOrderService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import com.ego.redis.dao.JedisDao;

@Service
public class TbOrderServiceImpl implements TbOrderService {

	@Resource
	private JedisDao jedisDaoImpl;

	@Value("${cart.key}")
	private String cartKey;

	@Value("${passport.url}")
	private String passportKey;

	@Reference
	private TbOrderDubboService tbOrderDubboServiceImpl;

	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;

	@SuppressWarnings("unchecked")
	@Override
	public List<TbItemChild> showOrderCart(List<Long> idList, HttpServletRequest request) {
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String result = HttpClientUtil.doPost(passportKey + token);
		EgoResult er = JsonUtils.jsonToPojo(result, EgoResult.class);
		String key = cartKey + ((LinkedHashMap<String, String>) er.getData()).get("username");
		String json = jedisDaoImpl.get(key);
		List<TbItemChild> tbItemChildList = JsonUtils.jsonToList(json, TbItemChild.class);
		List<TbItemChild> tbItemChildOrderList = new ArrayList<>();
		for (TbItemChild tempTbItemChild : tbItemChildList) {
			for (Long tempId : idList) {
				if ((long) tempTbItemChild.getId() == (long) tempId) {
					// 实现【有货/无货】业务代码
					TbItem tempTbItem = tbItemDubboServiceImpl.selById(tempId);
					if (tempTbItem.getNum() >= tempTbItemChild.getNum()) {
						tempTbItemChild.setEnough(true);
					} else {
						tempTbItemChild.setEnough(false);
					}
					tbItemChildOrderList.add(tempTbItemChild);
				}
			}
		}
		return tbItemChildOrderList;
	}

	@Override
	public EgoResult create(MyOrderParam param, HttpServletRequest request) {

		// 【用户】数据
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String result = HttpClientUtil.doPost(passportKey + token);
		EgoResult er = JsonUtils.jsonToPojo(result, EgoResult.class);
		Map user = (LinkedHashMap<String, String>) (er.getData());

		// 【订单表】数据添加
		TbOrder order = new TbOrder();
		order.setPayment(param.getPayment());
		order.setPaymentType(param.getPaymentType());
		String orderId = IDUtils.genItemId() + "";
		order.setOrderId(orderId + "");
		Date date = new Date();
		order.setCreateTime(date);
		order.setUpdateTime(date);
		order.setUserId(Long.parseLong(user.get("id").toString()));
		order.setBuyerNick(user.get("username").toString());
		order.setBuyerRate(0);

		// 【订单-商品表】数据添加
		for (TbOrderItem eachTbOrderItem : param.getOrderItems()) {
			eachTbOrderItem.setOrderId(orderId);
			eachTbOrderItem.setId(IDUtils.genItemId() + "");
		}

		// 【收货人信息表】数据添加
		TbOrderShipping tbOrderShipping = param.getOrderShipping();
		tbOrderShipping.setOrderId(orderId);
		tbOrderShipping.setCreated(date);
		tbOrderShipping.setUpdated(date);

		// 把【订单表】、【订单-商品表】、【收货人信息表】写入数据库，返回结果数据
		EgoResult egoResult = new EgoResult();
		try {
			int index = tbOrderDubboServiceImpl.insOrder(order, param.getOrderItems(), tbOrderShipping);
			if (index > 0) {
				egoResult.setStatus(200);
				// 从redis中删除【已提交订单的购物车商品】
				String key = cartKey + user.get("username");
				String json = jedisDaoImpl.get(key);
				List<TbItemChild> listCart = JsonUtils.jsonToList(json, TbItemChild.class);
				List<TbItemChild> listToDelete = new ArrayList<TbItemChild>();
				for (TbItemChild eachTbItemChildInCart : listCart) {
					for (TbOrderItem eachTbOrderItem : param.getOrderItems()) {
						if (eachTbItemChildInCart.getId().toString().equals(eachTbOrderItem.getItemId())) {
							listToDelete.add(eachTbItemChildInCart);
						}
					}
				}
				listCart.removeAll(listToDelete);
				String listCartJsonAfterDelete = JsonUtils.objectToJson(listCart);
				jedisDaoImpl.set(key, listCartJsonAfterDelete);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return egoResult;
	}

}
