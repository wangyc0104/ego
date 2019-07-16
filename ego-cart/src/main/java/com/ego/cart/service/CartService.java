package com.ego.cart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;

/**
 * 购物车Service接口（服务消费者）
 * @author 王以诚
 */
public interface CartService {
	
	/**
	 * 加入购物车
	 * @param id 商品id
	 * @param num 商品数量
	 * @param request request对象
	 */
	public void addCart(long id, int num, HttpServletRequest request);
	
	/**
	 * 显示购物车
	 * @return 包含购物车内对象的List
	 */
	public List<TbItemChild> showCart(HttpServletRequest request);
	
	/**
	 * 根据id修改数量
	 * @param id 商品id
	 * @param num 商品数量
	 * @param request request对象
	 * @return 返回给前端的EgoResult
	 */
	public EgoResult updateNum(long id, int num, HttpServletRequest request);
	
	/**
	 * 删除购物车商品
	 * @param id 商品id
	 * @param request request对象
	 * @return 返回给前端的EgoResult
	 */
	public EgoResult delete(long id, HttpServletRequest request);
	
}
