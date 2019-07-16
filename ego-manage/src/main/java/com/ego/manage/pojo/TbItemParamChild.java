package com.ego.manage.pojo;

import com.ego.pojo.TbItemParam;

/**
 * 为了向前台传输【商品类目的名字】而创建的一个表示【商品参数】的类，继承自【商品参数】类
 * @author 王以诚
 */
public class TbItemParamChild extends TbItemParam {
	
	private String itemCatName;

	public String getItemCatName() {
		return itemCatName;
	}

	public void setItemCatName(String itemCatName) {
		this.itemCatName = itemCatName;
	}

}
