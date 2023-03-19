package com.ykmxxi.aligong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.ykmxxi.aligong.domain.Event;
import com.ykmxxi.aligong.domain.QEvent;

public interface EventRepository extends
	JpaRepository<Event, Long>,
	QuerydslPredicateExecutor<Event>,
	QuerydslBinderCustomizer<QEvent> {

	@Override
	default void customize(QuerydslBindings bindings, QEvent root) {
		bindings.excludeUnlistedProperties(true);
		bindings.including(root.placeId, root.eventName, root.eventStatus, root.eventStartDatetime,
			root.eventEndDatetime);
		bindings.bind(root.eventName).first(StringExpression::likeIgnoreCase);
		bindings.bind(root.eventStartDatetime).first(ComparableExpression::goe);
		bindings.bind(root.eventEndDatetime).first(ComparableExpression::loe);
	}
}
