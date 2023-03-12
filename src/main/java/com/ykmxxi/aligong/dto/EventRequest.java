package com.ykmxxi.aligong.dto;

import java.time.LocalDateTime;

import com.ykmxxi.aligong.constant.EventStatus;

public record EventRequest(
	Long placeId,
	String eventName,
	EventStatus eventStatus,
	LocalDateTime eventStartDatetime,
	LocalDateTime eventEndDatetime,
	Integer currentNumberOfPeople,
	Integer capacity,
	String memo
) {
	public static EventRequest of(
		Long placeId,
		String eventName,
		EventStatus eventStatus,
		LocalDateTime eventStartDatetime,
		LocalDateTime eventEndDatetime,
		Integer currentNumberOfPeople,
		Integer capacity,
		String memo
	) {
		return new EventRequest(
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

	public EventDto toDto() {
		return EventDto.of(
			this.placeId(),
			this.eventName(),
			this.eventStatus(),
			this.eventStartDatetime(),
			this.eventEndDatetime(),
			this.currentNumberOfPeople(),
			this.capacity(),
			this.memo(),
			null,
			null
		);
	}
}