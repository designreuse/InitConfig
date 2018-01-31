package com.ptsolution.core.orm.repo.impls;

import org.springframework.stereotype.Repository;

import com.ptsolution.core.orm.custom.CustomQueryDslJpaRepositoryImpls;
import com.ptsolution.core.orm.custom.JpaPersistence;
import com.ptsolution.core.orm.entity.QRole;
import com.ptsolution.core.orm.entity.QUser;
import com.ptsolution.core.orm.entity.User;
import com.ptsolution.core.orm.repo.UserRepo;

@Repository
public class UserRepoImpls extends CustomQueryDslJpaRepositoryImpls<User, String> implements UserRepo {

	public UserRepoImpls(JpaPersistence entityManager) {
		super(User.class, entityManager.getEntityManager());
	}

	@Override
	public User findOneWithRole(String userId) {
		QUser user = QUser.user;
		QRole role = QRole.role;
		
		return (User) createJPQLQuery(user.userId.eq(userId)).distinct()
				.leftJoin(user.roles, role).fetchJoin().fetchOne();
	}

}
