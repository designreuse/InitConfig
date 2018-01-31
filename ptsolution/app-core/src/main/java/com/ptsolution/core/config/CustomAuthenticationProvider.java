package com.ptsolution.core.config;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import com.ptsolution.core.orm.entity.User;
import com.ptsolution.core.orm.service.UserService;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	private static final Log log = LogFactory.getLog(CustomAuthenticationProvider.class);
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		User user = userService.findOneWithRole(username);
		
		log.info("************ username :" + username + " password: " + password);

		if (user == null)
			throw new BadCredentialsException("login.notFound");

		if (!password.equals(user.getPassword()))
			throw new BadCredentialsException("login.badCredential");

		Collection<GrantedAuthority> gas = PTSecurityUtil.toGrantedAuthorities(user.getRoles());

		if (gas == null || gas.size() == 0)
			throw new BadCredentialsException("no permission");
		return new UsernamePasswordAuthenticationToken(user, password, gas);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
