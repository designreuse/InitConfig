package com.ptsolution.core.orm.custom;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;

public interface CustomQueryDslJpaRepository<T, ID extends Serializable> extends QueryDslPredicateExecutor<T>, JpaRepository<T, ID> {
	JPAQuery<?> createJPAQuery();
	JPQLQuery<?> createJPQLQuery(Predicate... predicate);
	JPQLQuery<?> createCountQuery(Predicate... predicate);
	Querydsl getQuerydsl();
	Page<T> findAll(String entityGraph, boolean isFetchGraph, Pageable pageAble, Predicate... predicates);
	T findOne(String entityGraph, boolean isFetchGraph, Predicate... predicates);
}
