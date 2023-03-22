package com.ykmxxi.aligong.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.ykmxxi.aligong.constant.ErrorCode;
import com.ykmxxi.aligong.constant.EventStatus;
import com.ykmxxi.aligong.domain.Place;
import com.ykmxxi.aligong.dto.EventDto;
import com.ykmxxi.aligong.exception.GeneralException;
import com.ykmxxi.aligong.repository.EventRepository;
import com.ykmxxi.aligong.repository.PlaceRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EventService {

	private final EventRepository eventRepository;
	private final PlaceRepository placeRepository;

	public List<EventDto> getEvents(Predicate predicate) {
		try {
			return StreamSupport.stream(eventRepository.findAll(predicate).spliterator(), false)
				.map(EventDto::of)
				.toList();
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}

	public List<EventDto> getEvents(
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

	public Optional<EventDto> getEvent(Long eventId) {
		try {
			return eventRepository.findById(eventId).map(EventDto::of);
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}

	public boolean createEvent(EventDto eventDTO) {
		try {
			if (eventDTO == null) {
				return false;
			}

			Place place = placeRepository.findById(eventDTO.placeDto().id())
				.orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));
			eventRepository.save(eventDTO.toEntity(place));
			return true;
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}

	public boolean modifyEvent(Long eventId, EventDto dto) {
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
