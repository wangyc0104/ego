package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbItemCat;

/**
 * 门户页面左侧商品类目dubbo-service-provider
 * @author 王以诚
 *
 */
public interface TbItemCatDubboService {

	/**
	 * 根据父类目id查询所有的子类目
	 * @param pid
	 * @return
	 */
	List<TbItemCat> show(long pid);
	
	/**
	 * 根据类目id查询
	 * @param id
	 * @return
	 */
	TbItemCat selById(long id);
}
