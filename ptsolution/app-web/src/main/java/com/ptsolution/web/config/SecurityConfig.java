package com.ptsolution.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.ptsolution.core.config.CustomAuthenticationProvider;
import com.ptsolution.core.orm.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired private UserService userService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login.htm").permitAll()
				.loginProcessingUrl("/j_spring_security_check")
				.defaultSuccessUrl("/secure/home.htm", true)
				.usernameParameter("username")
				.passwordParameter("password")
				.and()
			.logout()
				.logoutUrl("/logout.htm")
				.logoutSuccessUrl("/login.htm?m=ss")
				.deleteCookies("JSESSIONID")
				.invalidateHttpSession(true)
				.and()
			.csrf()
				.and()
			.httpBasic()
				.and()
			.headers().frameOptions().sameOrigin();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(customAuthenticationProvider());
	}

	@Bean
	protected AuthenticationProvider customAuthenticationProvider(){
		CustomAuthenticationProvider authentication = new CustomAuthenticationProvider();
		authentication.setUserService(userService);
		return authentication;
	}
}
