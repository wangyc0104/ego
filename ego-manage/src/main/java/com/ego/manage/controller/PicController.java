package com.ego.manage.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ego.manage.service.PicService;

/**
 * 图片上传控制器（注意检查前台页面是否enctype="multipart/form-data"）
 * @author 王以诚
 */
@Controller
public class PicController {
	
	@Autowired
	private PicService picService;
	
	/**
	 * 执行图片上传
	 * @param uploadFile 从页面post过来的文件
	 * @return 包含了状态信息的map对象
	 */
	@RequestMapping("pic/upload")
	@ResponseBody
	public Map<String, Object> upload(MultipartFile uploadFile){
		return picService.upload(uploadFile);
	}
	
}
