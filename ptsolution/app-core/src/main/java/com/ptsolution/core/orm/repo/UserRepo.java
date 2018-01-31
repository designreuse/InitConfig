package com.ptsolution.core.orm.repo;

import com.ptsolution.core.orm.custom.CustomQueryDslJpaRepository;
import com.ptsolution.core.orm.entity.User;

public interface UserRepo extends CustomQueryDslJpaRepository<User, String>{

	User findOneWithRole(String userId);

}
