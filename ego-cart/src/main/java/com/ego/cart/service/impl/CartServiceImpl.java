package com.ego.cart.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.redis.dao.JedisDao;

@Service
public class CartServiceImpl implements CartService {

	@Resource
	private JedisDao jedisDaoImpl;
	
	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	
	@Value("${passport.url}")
	private String passportUrl;
	
	@Value("${cart.key}")
	private String cartKey;
	
	@SuppressWarnings("unchecked")
	@Override
	public void addCart(long id, int num, HttpServletRequest request) {
		
		// 购物车商品信息list
		List<TbItemChild> tbItemChildList = new ArrayList<>();
		
		// 从cookie中获取TT_TOKEN的值作为key，然后从服务器中获取tbUser的数据（封装在EgoResult的data属性中）
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = HttpClientUtil.doPost(passportUrl + token);
		EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);
		
		// redis中[用户-购物车中商品信息]的key
		String key = cartKey + ((LinkedHashMap<String, Object>) er.getData()).get("username");
		
		// redis中是否有与key对应的购物车信息
		if (jedisDaoImpl.exists(key)) { // 有购物车信息
			String json = jedisDaoImpl.get(key);
			if (json != null && !json.equals("")) {
				tbItemChildList = JsonUtils.jsonToList(json, TbItemChild.class);
				for (TbItemChild tbItemChild : tbItemChildList) {
					// 判断购物车是否有正在添加的商品
					if ((long) tbItemChild.getId() == id) {
						// 把商品数量加上相应num
						tbItemChild.setNum(tbItemChild.getNum() + num);
						// 把购物车信息重新添加至redis
						jedisDaoImpl.set(key, JsonUtils.objectToJson(tbItemChildList));
						return;
					}
				}
			}
		}
		TbItem tbItem = tbItemDubboServiceImpl.selById(id);
		// 创建带image数组属性的tbItemChild对象，将tbItem对象的值赋给tbItemChild
		TbItemChild tbItemChild = new TbItemChild();
		tbItemChild.setId(tbItem.getId());
		tbItemChild.setTitle(tbItem.getTitle());
		tbItemChild.setImages((tbItem.getImage() == null || tbItem.getImage().equals("")) ? new String[1] : tbItem.getImage().split(","));
		tbItemChild.setNum(num);
		tbItemChild.setPrice(tbItem.getPrice());
		tbItemChildList.add(tbItemChild);
		// 在redis中将用户名信息存储到cart:userid的key中（EgoResult的data返回的是LinkedHashMap类型的对象）
		jedisDaoImpl.set(key, JsonUtils.objectToJson(tbItemChildList));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TbItemChild> showCart(HttpServletRequest request) {
		// 从cookie中获取TT_TOKEN的值作为key，然后从服务器中获取tbUser的数据（封装在EgoResult的data属性中）
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = HttpClientUtil.doPost(passportUrl + token);
		EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);

		// redis中根据[用户-购物车中商品信息]的key获取内容
		String key = cartKey + ((LinkedHashMap<String, Object>) er.getData()).get("username");
		String json = jedisDaoImpl.get(key);
		return JsonUtils.jsonToList(json, TbItemChild.class);
	}

	@Override
	public EgoResult updateNum(long id, int num, HttpServletRequest request) {
		// 从cookie中获取TT_TOKEN的值作为key，然后从服务器中获取tbUser的数据（封装在EgoResult的data属性中）
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = HttpClientUtil.doPost(passportUrl + token);
		EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);
		String key = cartKey + ((LinkedHashMap<String, Object>) er.getData()).get("username");

		String json = jedisDaoImpl.get(key);
		List<TbItemChild> list = JsonUtils.jsonToList(json, TbItemChild.class);
		for (TbItemChild tbItemChild : list) {
			if ((long) tbItemChild.getId() == id) {
				tbItemChild.setNum(num);
			}
		}
		// 修改后的商品数量的list照样转换为json字符串，放回redis中
		String ok = jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
		EgoResult egoResult = new EgoResult();
		if (ok.equals("OK")) {
			egoResult.setStatus(200);
		}
		return egoResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EgoResult delete(long id, HttpServletRequest request) {
		// 从cookie中获取TT_TOKEN的值作为key，然后从服务器中获取tbUser的数据（封装在EgoResult的data属性中）
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		String jsonUser = HttpClientUtil.doPost(passportUrl + token);
		EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);
		String key = cartKey + ((LinkedHashMap<String, Object>) er.getData()).get("username");
		// 获取购物车列表
		String json = jedisDaoImpl.get(key);
		List<TbItemChild> list = JsonUtils.jsonToList(json, TbItemChild.class);
		TbItemChild tbItemChildDeleted = null;
		for (TbItemChild tbItemChild : list) {
			// 从购物车中删除相应id的商品
			if ((long) tbItemChild.getId() == id) {
				tbItemChildDeleted = tbItemChild;
			}
		}
		list.remove(tbItemChildDeleted);
		// 把list重新转回json，存入redis，成功后返回一个status为200的egoResult
		String ok = jedisDaoImpl.set(key, JsonUtils.objectToJson(list));
		EgoResult egoResult = new EgoResult();
		if (ok.equals("OK")) {
			egoResult.setStatus(200);
		}
		return egoResult;
	}

}
