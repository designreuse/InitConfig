package com.ptsolution.core.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.ptsolution.core.orm.entity.Role;

public class PTSecurityUtil {
	
	public static Collection<GrantedAuthority> toGrantedAuthorities(Collection<Role> roles){
		Collection<GrantedAuthority> gas = new ArrayList<GrantedAuthority>();
		for(Role role : roles){
			gas.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return gas;
	}
}
