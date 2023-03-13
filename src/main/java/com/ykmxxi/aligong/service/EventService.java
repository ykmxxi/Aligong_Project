package com.ykmxxi.aligong.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ykmxxi.aligong.constant.ErrorCode;
import com.ykmxxi.aligong.constant.EventStatus;
import com.ykmxxi.aligong.dto.EventDto;
import com.ykmxxi.aligong.exception.GeneralException;
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
		try {
			return eventRepository.findEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime);
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}

	public Optional<EventDto> getEvent(Long eventId) {
		try {
			return eventRepository.findEvent(eventId);
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}

	public boolean createEvent(EventDto eventDto) {
		try {
			return eventRepository.insertEvent(eventDto);
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}

	public boolean modifyEvent(Long eventId, EventDto eventDto) {
		try {
			return eventRepository.updateEvent(eventId, eventDto);
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}

	public boolean removeEvent(Long eventId) {
		try {
			return eventRepository.deleteEvent(eventId);
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}
}
