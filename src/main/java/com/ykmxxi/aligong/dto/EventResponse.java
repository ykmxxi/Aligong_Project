package com.ykmxxi.aligong.dto;

import java.time.LocalDateTime;

import com.ykmxxi.aligong.constant.EventStatus;

public record EventResponse(
	Long placeId,
	String eventName,
	EventStatus eventStatus,
	LocalDateTime eventStartDatetime,
	LocalDateTime eventEndDatetime,
	Integer currentNumberOfPeople,
	Integer capacity,
	String memo
) {
	public static EventResponse of(
		Long placeId,
		String eventName,
		EventStatus eventStatus,
		LocalDateTime eventStartDatetime,
		LocalDateTime eventEndDatetime,
		Integer currentNumberOfPeople,
		Integer capacity,
		String memo
	) {
		return new EventResponse(
			placeId,
			eventName,
			eventStatus,
			eventStartDatetime,
			eventEndDatetime,
			currentNumberOfPeople,
			capacity,
			memo
		);
	}

	public static EventResponse from(EventDto eventDto) {
		if (eventDto == null) {
			return null;
		}

		return EventResponse.of(
			eventDto.placeId(),
			eventDto.eventName(),
			eventDto.eventStatus(),
			eventDto.eventStartDatetime(),
			eventDto.eventEndDatetime(),
			eventDto.currentNumberOfPeople(),
			eventDto.capacity(),
			eventDto.memo()
		);
	}
}