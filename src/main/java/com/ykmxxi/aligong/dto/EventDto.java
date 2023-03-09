package com.ykmxxi.aligong.dto;

import java.time.LocalDateTime;

import com.ykmxxi.aligong.constant.EventStatus;

public record EventDto(
	Long placeId,
	String eventName,
	EventStatus eventStatus,
	LocalDateTime eventStartDatetime,
	LocalDateTime eventEndDatetime,
	Integer currentNumberOfPeople,
	Integer capacity,
	String memo,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
	public static EventDto of(
		Long placeId,
		String eventName,
		EventStatus eventStatus,
		LocalDateTime eventStartDatetime,
		LocalDateTime eventEndDatetime,
		Integer currentNumberOfPeople,
		Integer capacity,
		String memo,
		LocalDateTime createdAt,
		LocalDateTime modifiedAt
	) {
		return new EventDto(
			placeId,
			eventName,
			eventStatus,
			eventStartDatetime,
			eventEndDatetime,
			currentNumberOfPeople,
			capacity,
			memo,
			createdAt,
			modifiedAt
		);
	}
}
