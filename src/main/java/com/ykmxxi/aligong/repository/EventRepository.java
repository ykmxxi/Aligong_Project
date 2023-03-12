package com.ykmxxi.aligong.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ykmxxi.aligong.constant.EventStatus;
import com.ykmxxi.aligong.dto.EventDto;

public interface EventRepository {

	// TODO: 2023/03/12 repository layer 구현 완료 시 default 삭제
	default List<EventDto> findEvents(
		Long placeId,
		String eventName,
		EventStatus eventStatus,
		LocalDateTime eventStartDatetime,
		LocalDateTime eventEndDatetime
	) {
		return null;
	}

	default Optional<EventDto> findEvent(Long eventId) {
		return Optional.empty();
	}

	default boolean insertEvent(EventDto eventDTO) {
		return false;
	}

	default boolean updateEvent(Long eventId, EventDto dto) {
		return false;
	}

	default boolean deleteEvent(Long eventId) {
		return false;
	}
}
