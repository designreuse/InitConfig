package com.ptsolution.core.orm.custom;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import com.querydsl.jpa.impl.JPAQuery;

public class CustomQueryDslJpaRepositoryImpls<T, ID extends Serializable> extends QueryDslJpaRepository<T, ID> implements CustomQueryDslJpaRepository<T, ID> {
	private EntityManager entityManager;
	private final EntityPath<T> path;
	private final Querydsl queryDls;
	private final PathBuilder<T> builder;
	
	public void setEntityManager(JpaPersistence jpaPersistence) {
		this.entityManager = jpaPersistence.getEntityManager();
	}
	
	public CustomQueryDslJpaRepositoryImpls(Class<T> domainClass,
			EntityManager entityManager) {
		super(new JpaMetamodelEntityInformation<T, ID>(domainClass, entityManager.getMetamodel()), entityManager);
		this.entityManager = entityManager;
		this.path = SimpleEntityPathResolver.INSTANCE.createPath(domainClass);
		this.builder = new PathBuilder<T>(path.getType(), path.getMetadata());
		this.queryDls = new Querydsl(entityManager, builder);
	}

	public JPAQuery<?> createJPAQuery() {
		return new JPAQuery<T>(entityManager);
	}

	public JPQLQuery<?> createJPQLQuery(Predicate... predicate) {
		return createQuery(predicate);
	}

	public JPQLQuery<?> createCountQuery(Predicate... predicate) {
		return createCountQuery(ExpressionUtils.allOf(predicate));
	}

	public Querydsl getQuerydsl() {
		return queryDls;
	}

	public JPQLQuery<?> createQuery(String entityGraph, boolean isFetchGraph, Predicate... predicates){
		AbstractJPAQuery<?, ?> query = (AbstractJPAQuery<?, ?>) createQuery(predicates);
		
		if(StringUtils.isNotBlank(entityGraph)){
			query.setHint(isFetchGraph ? "javax.persistence.fetchgraph" : "javax.persistence.loadgraph",
					entityManager.getEntityGraph(entityGraph));
		}
		
		return query;
	}
	
	public Page<T> findAll(String entityGraph, boolean isFetchGraph, Pageable pageAble, Predicate... predicates) {
		long countQuery = createCountQuery(predicates).fetchCount();
		JPQLQuery<T> query = queryDls.applyPagination(pageAble, createQuery(entityGraph, isFetchGraph, predicates).select(path));
		
		List<T> content = pageAble == null || countQuery > pageAble.getOffset() ? query.fetch() : Collections.<T>emptyList();
		return new PageImpl<T>(content, pageAble, countQuery);
	}

	@SuppressWarnings("unchecked")
	public T findOne(String entityGraph, boolean isFetchGraph, Predicate... predicates) {
		return (T) createQuery(entityGraph, isFetchGraph, predicates).fetchOne();
	}
}
