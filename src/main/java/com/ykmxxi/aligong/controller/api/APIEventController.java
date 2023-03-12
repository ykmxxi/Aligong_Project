package com.ykmxxi.aligong.controller.api;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ykmxxi.aligong.constant.EventStatus;
import com.ykmxxi.aligong.dto.APIDataResponse;
import com.ykmxxi.aligong.dto.EventRequest;
import com.ykmxxi.aligong.dto.EventResponse;
import com.ykmxxi.aligong.service.EventService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class APIEventController {

	private final EventService eventService;

	@GetMapping("/events")
	public APIDataResponse<List<EventResponse>> getEvents(
		Long placeId,
		String eventName,
		EventStatus eventStatus,
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartDatetime,
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndDatetime
	) {
		List<EventResponse> responses = eventService
			.getEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime)
			.stream().map(EventResponse::from)
			.toList();

		return APIDataResponse.of(responses);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/events")
	public APIDataResponse<String> createEvent(@RequestBody EventRequest eventRequest) {
		boolean result = eventService.createEvent(eventRequest.toDto());

		return APIDataResponse.of(Boolean.toString(result));
	}

	@GetMapping("/events/{eventId}")
	public APIDataResponse<EventResponse> getEvent(@PathVariable Long eventId) {
		EventResponse eventResponse = EventResponse.from(eventService.getEvent(eventId)
			.orElse(null));

		return APIDataResponse.of(eventResponse);
	}

	@PutMapping("/events/{eventId}")
	public APIDataResponse<String> modifyEvent(
		@PathVariable Long eventId,
		@RequestBody EventRequest eventRequest
	) {
		boolean result = eventService.modifyEvent(eventId, eventRequest.toDto());

		return APIDataResponse.of(Boolean.toString(result));
	}

	@DeleteMapping("/events/{eventId}")
	public APIDataResponse<String> removeEvent(@PathVariable Long eventId) {
		boolean result = eventService.removeEvent(eventId);

		return APIDataResponse.of(Boolean.toString(result));
	}

}
