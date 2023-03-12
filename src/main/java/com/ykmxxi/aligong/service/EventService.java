package com.ykmxxi.aligong.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ykmxxi.aligong.constant.EventStatus;
import com.ykmxxi.aligong.dto.EventDto;
import com.ykmxxi.aligong.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EventService {

	private final EventRepository eventRepository;

	public List<EventDto> getEvents(
		Long placeId,
		String eventName,
		EventStatus eventStatus,
		LocalDateTime eventStartDatetime,
		LocalDateTime eventEndDatetime
	) {
		return eventRepository.findEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime);
	}

	public Optional<EventDto> getEvent(Long eventId) {
		return eventRepository.findEvent(eventId);
	}

	public boolean createEvent(EventDto eventDto) {
		return eventRepository.insertEvent(eventDto);
	}

	public boolean modifyEvent(Long eventId, EventDto eventDto) {
		return eventRepository.updateEvent(eventId, eventDto);
	}

	public boolean removeEvent(Long eventId) {
		return eventRepository.deleteEvent(eventId);
	}
}
