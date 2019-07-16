package com.ego.search.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ego.search.service.TbItemService;

/**
 * 商品搜索控制器
 * @author 王以诚
 */
@Controller
public class TbItemController {

	@Resource
	private TbItemService tbItemServiceImpl;

	/**
	 * solr索引库初始化（无页面，通过地址栏访问初始化）
	 * @return
	 */
	@RequestMapping(value = "solr/init", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String init() {
		System.out.println("初始化开始……");
		try {
			long start = System.currentTimeMillis();
			tbItemServiceImpl.init();
			long end = System.currentTimeMillis();
			return "初始化总时间：" + (end - start) / 1000 + "秒";
		} catch (Exception e) {
			e.printStackTrace();
			return "初始化失败！";
		}
	}

	@RequestMapping("search.html") // Ajax不允许向一个.html的地址发送请求
	public String search(Model model, String q, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "12") int rows) {
		try {
			q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
			Map<String, Object> map = tbItemServiceImpl.selByQuery(q, page, rows);
			model.addAttribute("query", q);
			model.addAttribute("itemList", map.get("itemList"));
			model.addAttribute("totalPages", map.get("totalPages"));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "search";
	}

	/**
	 * 新增功能，返回值为1的时候成功
	 * @param tbItem
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("solr/add")
	@ResponseBody
	public int add(@RequestBody Map<String, Object> map) {
		System.out.println(map);
		System.out.println(map.get("item"));
		System.out.println(map.get("desc"));
		try {
			return tbItemServiceImpl.add((LinkedHashMap<String, Object>) (map.get("item")), map.get("desc").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
