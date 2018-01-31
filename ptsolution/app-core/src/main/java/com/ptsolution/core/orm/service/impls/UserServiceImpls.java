package com.ptsolution.core.orm.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsolution.core.orm.entity.User;
import com.ptsolution.core.orm.repo.UserRepo;
import com.ptsolution.core.orm.service.UserService;

@Service
public class UserServiceImpls implements UserService {
	@Autowired private UserRepo dao;
	
	@Override
	public User findOneWithRole(String userId) {
		return dao.findOneWithRole(userId);
	}

	@Override
	public void save(User user) {
		dao.save(user);
	}

}
