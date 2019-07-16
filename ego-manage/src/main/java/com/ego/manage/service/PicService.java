package com.ego.manage.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

/**
 * 用于图片上传的service
 * @author 王以诚
 *
 */
public interface PicService {

	/**
	 * 文件上传
	 * @param file
	 * @return
	 */
	Map<String, Object> upload(MultipartFile file);

}
