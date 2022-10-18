package com.sdwhNrcc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {

	public FilterConfig config;
//	@Resource  
//	StringRedisTemplateUtil redisUtil;  

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("==≥ı ºªØ==");
	}
	public static boolean isContains(String container, String[] regx) {
		boolean result = false;

		for (int i = 0; i < regx.length; i++) {
			if (container.indexOf(regx[i]) != -1) {
				return true;
			}
		}
		return result;
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest) arg0;
		HttpServletResponse response=(HttpServletResponse) arg1;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
//		String basePath=request.getScheme()+"://"+request.getServerName()+":"
//				+request.getServerPort()+request.getContextPath()+"/";
//		HttpSession session=request.getSession();
//		session.setAttribute("basePath", basePath);
		arg2.doFilter(request, response);
	}

	@Override
	public void destroy() {
		System.out.println("==œ˙ªŸ==");
	}


}
