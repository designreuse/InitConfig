package com.ptsolution.core.orm.repo.impls;

import org.springframework.stereotype.Repository;

import com.ptsolution.core.orm.custom.CustomQueryDslJpaRepositoryImpls;
import com.ptsolution.core.orm.custom.JpaPersistence;
import com.ptsolution.core.orm.entity.Role;
import com.ptsolution.core.orm.repo.RoleRepo;

@Repository
public class RoleRepoImpls extends CustomQueryDslJpaRepositoryImpls<Role, String> implements RoleRepo {

	public RoleRepoImpls(JpaPersistence entityManager) {
		super(Role.class, entityManager.getEntityManager());
	}

}
