package com.ego.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;

/**
 * 登录拦截器
 * @author 王以诚
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 判断用户是否登录
		String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
		System.out.println("token: " + token);
		if (token != null && !token.equals("")) {
			String json = HttpClientUtil.doPost("http://localhost:8084/user/token/" + token);
			System.out.println(json);
			EgoResult er = JsonUtils.jsonToPojo(json, EgoResult.class);
			if (er.getStatus() == 200) {
				return true;
			}
		}
		String num = request.getParameter("num");
		if (num != null && !num.equals("")) {
			response.sendRedirect("http://localhost:8084/user/showLogin?interurl=" + request.getRequestURL() + "%3Fnum=" + num);
		} else {
			response.sendRedirect("http://localhost:8084/user/showLogin");
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception { }

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception { }

}
