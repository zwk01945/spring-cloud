package com.cloud.handler;

import com.cloud.properter.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**************************************************************
 ***       S  T  A  G  E    多模块依赖项目                    ***
 **************************************************************
 *                                                            *
 *         Project Name : base                                *
 *                                                            *
 *         File Name : DefenseInterceptor.java                *
 *                                                            *
 *         Programmer : Mr.zhang                              *
 *                                                            *
 *         Start Date : 2020/7/24 17:28                       *
 *                                                            *
 *         Last Update : 2020/7/24 17:28                      *
 *                                                            *
 *------------------------------------------------------------*
 * 功能:                                                       *
 *   全局字符过滤器                                              *
 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - -  */
public class DefenseInterceptor implements HandlerInterceptor {


	private static final Logger logger = LoggerFactory.getLogger(DefenseInterceptor.class);
	public static final String origins = Config.getInstance().getProperty("origins");

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		logger.debug("进入特殊字符拦截器");
		if (request.getMethod().equalsIgnoreCase("post")) {
			request.setCharacterEncoding("utf-8");
		} else {
			for (String[] params : request.getParameterMap().values()) {
				int len = params.length;
				for (int i = 0; i < len; i++) {
					params[i] = new String(params[i].getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
				}
			}
		}
		// 过滤客户端提交表单中特殊字符
		for (String[] params : request.getParameterMap().values()) {
			for (String param : params) {
				boolean validateTool = DefenseUtil.validateTool(param);
				if (!validateTool) {
					response.reset();
					response.setCharacterEncoding("utf-8");
					response.setHeader("Access-Control-Allow-Origin", origins);
					response.setHeader("Access-Control-Allow-Credentials", "true");
					PrintWriter out = response.getWriter();
//					out.println("ERRDATA"+params[i]);
					out.println("{\"ERRDATA\":\"" + param + "\"}");
					out.close();
					return false;
				}
			}

		}
		response.setCharacterEncoding("utf-8");
		return true;
	}
}
