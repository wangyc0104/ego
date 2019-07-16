package com.ego.passport.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbUser;

/**
 * 用户登录service
 * @author 王以诚
 *
 */
public interface TbUserService {
	
	/**
	 * 登录
	 * @param user
	 * @return
	 */
	public EgoResult login(TbUser user, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 根据token查询用户信息
	 * @param token
	 * @return
	 */
	EgoResult getUserInfoByToken(String token);
	
	/**
	 * 用户安全登出
	 * @param token
	 * @return
	 */
	EgoResult logout(String token, HttpServletRequest request, HttpServletResponse response);
	
}
