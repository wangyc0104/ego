package com.ego.manage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.redis.dao.JedisDao;

@Service
public class TbItemServiceImpl implements TbItemService {

	@Reference
	private TbItemDubboService tbItemDubboServiceImpl;
	@Reference
	private TbItemDescDubboService tbItemDescDubboService;
	@Value("${search.url}")
	private String url;
	@Resource
	private JedisDao jedisDaoImpl;
	@Value("${redis.item.key}")
	private String itemKey;

	@Override
	public EasyUIDataGrid show(int page, int rows) {
		System.out.println("page = " + page);
		System.out.println("rows = " + rows);
		System.out.println(tbItemDubboServiceImpl);
		return tbItemDubboServiceImpl.show(page, rows);
	}

	@Override
	public int update(String ids, byte status) {
		int index = 0;
		TbItem item = new TbItem();
		String[] idsStr = ids.split(",");
		for (String id : idsStr) {
			item.setId(Long.parseLong(id));
			item.setStatus(status);
			index += tbItemDubboServiceImpl.updItemStatus(item);
			// 若下架商品或删除商品，则需要将商品缓存信息从Redis中删除
			if (status == 2 || status == 3) {
				jedisDaoImpl.del(itemKey + id);
			}
		}
		if (index == idsStr.length) {
			return 1;
		}
		return 0;
	}

	/**
	 * 注意：该功能不能进行事务回滚，因为该功能并未在Dubbo里面写
	 */
	@Override
	public int save(TbItem item, String desc, String itemParams) {
		// 不考虑事务回滚
//		long id = IDUtils.genItemId();
//		item.setId(id);
//		Date date = new Date();
//		item.setCreated(date);
//		item.setUpdated(date);
//		item.setStatus((byte) 1);
//		int index = tbItemDubboServiceImpl.insTbItem(item);
//		if (index > 0) {
//			TbItemDesc itemDesc = new TbItemDesc();
//			itemDesc.setItemDesc(desc);
//			itemDesc.setItemId(id);
//			itemDesc.setCreated(date);
//			itemDesc.setUpdated(date);
//			index += tbItemDescDubboService.insDesc(itemDesc);
//		}
//		if (index == 2) {
//			return 1;
//		}
		
		// 调用Dubbo中考虑事务回滚功能的方法
		long id = IDUtils.genItemId();
		item.setId(id);
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		item.setStatus((byte) 1);
		
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setCreated(date);
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(id);
		itemDesc.setUpdated(date);
		
		TbItemParamItem paramItem = new TbItemParamItem();
		paramItem.setCreated(date);
		paramItem.setItemId(id);
		paramItem.setParamData(itemParams);
		paramItem.setUpdated(date);
		
		int index = 0;
		try {
			index = tbItemDubboServiceImpl.insTbItemDesc(item, itemDesc, paramItem);
			System.out.println("index184: " + index);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 用另一线程让新增条目的时候使用HttpClient将后台solr数据同步
		final TbItem itemFinal = item;
		final String descFinal = desc;
		new Thread(){
			public void run() {
				Map<String, Object> map = new HashMap<>();
				map.put("item",itemFinal);
				map.put("desc", descFinal);
				HttpClientUtil.doPostJson(url, JsonUtils.objectToJson(map));
			}
		}.start();
		
		return index;
	}

}
