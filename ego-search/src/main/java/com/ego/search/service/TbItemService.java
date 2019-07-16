package com.ego.search.service;

import java.io.IOException;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

import com.ego.pojo.TbItem;

/**
 * 商品搜索service
 * @author 王以诚
 */
public interface TbItemService {

	/**
	 * 初始化
	 */
	public void init() throws SolrServerException, IOException;

	/**
	 * 分页查询代码
	 * @param query
	 * @return
	 */
	public Map<String, Object> selByQuery(String query, int page, int rows) throws SolrServerException, IOException;

	/**
	 * 增加
	 * @param tbItem
	 * @return
	 */
	public int add(Map<String, Object> map, String desc) throws SolrServerException, IOException;

}
