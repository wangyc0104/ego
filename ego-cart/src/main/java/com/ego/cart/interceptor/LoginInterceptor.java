package com.ego.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
//		// 查阅一下各种方法输出的URL格式
//		System.out.println("request.getRequestURL(): " + request.getRequestURL());
//		System.out.println("request.getRequestURI()" + request.getRequestURI());
//		System.out.println("request.getLocalAddr()" + request.getLocalAddr());
//		System.out.println("request.getRemoteAddr()" + request.getRemoteAddr());
		String num = request.getParameter("num");
		response.sendRedirect("http://localhost:8084/user/showLogin?interurl=" + request.getRequestURL() + "%3Fnum=" + num);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// 无代码
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// 无代码
	}

}
