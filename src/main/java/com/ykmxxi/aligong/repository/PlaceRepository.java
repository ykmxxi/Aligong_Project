package com.ykmxxi.aligong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.StringExpression;
import com.ykmxxi.aligong.domain.Place;
import com.ykmxxi.aligong.domain.QPlace;

public interface PlaceRepository extends
	JpaRepository<Place, Long>,
	QuerydslPredicateExecutor<Place>,
	QuerydslBinderCustomizer<QPlace> {

	@Override
	default void customize(QuerydslBindings bindings, QPlace root) {
		bindings.excludeUnlistedProperties(true);
		bindings.including(root.placeName, root.address, root.phoneNumber);
		bindings.bind(root.placeName).first(StringExpression::containsIgnoreCase);
		bindings.bind(root.address).first(StringExpression::containsIgnoreCase);
		bindings.bind(root.phoneNumber).first(StringExpression::containsIgnoreCase);
	}

}
