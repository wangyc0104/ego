package com.ego.dubbo.service;

import java.util.List;

import com.ego.pojo.TbContentCategory;

/**
 * 商品类目dubbo-service-provider
 * @author 王以诚
 *
 */
public interface TbContentCategoryDubboService {
	
	/**
	 * 根据父id来查询所有的子类目
	 * @param pid 父id
	 * @return 所有的子类目
	 */
	List<TbContentCategory> selByPid(long pid);
	
	/**
	 * 新增一个内容分类
	 * @param cat
	 * @return
	 */
	int insTbContentCategory(TbContentCategory cat);
	
	/**
	 * 通过id修改isParent属性
	 * @param id
	 * @param isParent
	 * @return
	 */
	int updIsParentById(TbContentCategory cat);
	
	/**
	 * 根据内容分类的id查找相应的对象
	 * @param id
	 * @return
	 */
	TbContentCategory selById(long id);
	
}
