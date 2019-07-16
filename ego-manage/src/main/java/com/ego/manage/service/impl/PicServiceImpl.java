package com.ego.manage.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ego.commons.utils.FtpUtil;
import com.ego.manage.service.PicService;

@Service
public class PicServiceImpl implements PicService {

	/**
	 * ego-commons项目中commons.properties的属性：<br>
	 * ftpclient.host=192.168.80.143 <br>
	 * ftpclient.port=21 <br>
	 * ftpclient.username=ftpuser <br>
	 * ftpclient.password=123456 <br>
	 * ftpclient.basePath=/home/ftpuser/ <br>
	 * ftpclient.filePath=/ <br>
	 */
	@Value("${ftpclient.host}")
	private String host;
	@Value("${ftpclient.port}")
	private int port;
	@Value("${ftpclient.username}")
	private String username;
	@Value("${ftpclient.password}")
	private String password;
	@Value("${ftpclient.basePath}")
	private String basePath;
	@Value("${ftpclient.filePath}")
	private String filePath;

	@Override
	public Map<String, Object> upload(MultipartFile file) {
		Map<String, Object> map = new HashMap<>();
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String filename = UUID.randomUUID().toString() + suffix;
		boolean result = false;
		try {
			result = FtpUtil.uploadFile(host, port, username, password, basePath, filePath, filename, file.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (result) {
				map.put("error", 0);
				map.put("url", "http://" + host + ":80" + filePath + filename);
			} else {
				map.put("error", 1);
				map.put("msg", "图片上传失败");
			}
		}
		return map;
	}

}
