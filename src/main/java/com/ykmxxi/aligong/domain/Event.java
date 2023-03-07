package com.ykmxxi.aligong.domain;

import java.time.LocalDateTime;

import com.ykmxxi.aligong.constant.EventStatus;

import lombok.Data;

@Data
public class Event {

	private Long id;

	private Long placeId;
	private String eventName;
	private EventStatus eventStatus;
	private LocalDateTime eventStartDatetime;
	private LocalDateTime eventEndDatetime;
	private Integer currentNumberOfPeople;
	private Integer capacity;
	private String memo;

	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

}
