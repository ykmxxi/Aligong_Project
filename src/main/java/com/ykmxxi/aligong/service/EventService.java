package com.ykmxxi.aligong.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.ykmxxi.aligong.constant.ErrorCode;
import com.ykmxxi.aligong.constant.EventStatus;
import com.ykmxxi.aligong.dto.EventDTO;
import com.ykmxxi.aligong.exception.GeneralException;
import com.ykmxxi.aligong.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EventService {

	private final EventRepository eventRepository;

	public List<EventDTO> getEvents(Predicate predicate) {
		try {
			return StreamSupport.stream(eventRepository.findAll(predicate).spliterator(), false)
				.map(EventDTO::of)
				.toList();
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}

	public List<EventDTO> getEvents(
		Long placeId,
		String eventName,
		EventStatus eventStatus,
		LocalDateTime eventStartDatetime,
		LocalDateTime eventEndDatetime
	) {
		try {
			return null;
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}

	public Optional<EventDTO> getEvent(Long eventId) {
		try {
			return eventRepository.findById(eventId).map(EventDTO::of);
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}

	public boolean createEvent(EventDTO eventDto) {
		try {
			if (eventDto == null) {
				return false;
			}

			eventRepository.save(eventDto.toEntity());
			return true;
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}

	public boolean modifyEvent(Long eventId, EventDTO dto) {
		try {
			if (eventId == null || dto == null) {
				return false;
			}

			eventRepository.findById(eventId)
				.ifPresent(event -> eventRepository.save(dto.updateEntity(event)));

			return true;
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}

	public boolean removeEvent(Long eventId) {
		try {
			if (eventId == null) {
				return false;
			}

			eventRepository.deleteById(eventId);
			return true;
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}
}
