package com.ego.dubbo.service;

import com.ego.pojo.TbUser;

/**
 * 用户登录dubbo-service-provider
 * @author 王以诚
 */
public interface TbUserDubboService {

	/**
	 * 根据用户名和密码查询登录
	 * @param user
	 * @return
	 */
	TbUser selByUser(TbUser user);

}
