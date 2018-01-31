package com.ptsolution.web.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.ptsolution.core.orm.entity.User;
import com.ptsolution.core.orm.service.UserService;

public class LanguageComponent extends HandlerInterceptorAdapter {
	@Autowired private UserService userService;
	
	public static final String DEFAULT_PARAM_NAME = "locale";

	private String paramName = DEFAULT_PARAM_NAME;

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamName() {
		return this.paramName;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String newLocale = request.getParameter(this.paramName);
		if (newLocale != null) {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			if (localeResolver == null) {
				throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
			}
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = (User)auth.getPrincipal();
			
//			if(!user.getLanguage().equals(newLocale)){
//				user.setLanguage(newLocale);
				userService.save(user);
//				localeResolver.setLocale(request, response, StringUtils.parseLocaleString(newLocale));
//			}
		}
		return true;
	}
}
