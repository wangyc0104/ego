package com.ego.order.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ego.commons.pojo.EgoResult;
import com.ego.order.pojo.MyOrderParam;
import com.ego.order.service.TbOrderService;

/**
 * 订单控制器
 * @author 王以诚
 */
@Controller
public class OrderController {

	@Resource
	private TbOrderService tbOrderServiceImpl;

	/**
	 * 订单确认页面
	 * @param model
	 * @param idList
	 * @param request
	 * @return
	 */
	@RequestMapping("order/order-cart.html")
	public String showCartOrder(Model model, @RequestParam("id") List<Long> idList, HttpServletRequest request) {
		System.out.println("OrderController.showCartOrder - idList : " + idList);
		model.addAttribute("cartList", tbOrderServiceImpl.showOrderCart(idList, request));
		return "order-cart";
	}

	/**
	 * 订单创建
	 * @param param
	 * @param request
	 * @return
	 */
	@RequestMapping("order/create.html")
	public String createOrder(MyOrderParam param, HttpServletRequest request) {
		System.out.println(param);
		EgoResult egoResult = tbOrderServiceImpl.create(param, request);
		if (egoResult.getStatus() == 200) {
			return "my-orders";
		} else {
			request.setAttribute("message", "订单创建失败");
			return "error/exception";
		}
	}
	
}
