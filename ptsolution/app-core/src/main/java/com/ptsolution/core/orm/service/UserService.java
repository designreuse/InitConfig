package com.ptsolution.core.orm.service;

import com.ptsolution.core.orm.entity.User;

public interface UserService {

	User findOneWithRole(String userId);

	void save(User user);

}
