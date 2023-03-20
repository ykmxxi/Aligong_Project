package com.ykmxxi.aligong.controller.api;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ykmxxi.aligong.constant.EventStatus;
import com.ykmxxi.aligong.dto.APIDataResponse;
import com.ykmxxi.aligong.dto.EventRequest;
import com.ykmxxi.aligong.dto.EventResponse;
import com.ykmxxi.aligong.service.EventService;

import lombok.RequiredArgsConstructor;

@Deprecated
// @Validated
@RequiredArgsConstructor
// @RequestMapping("/api")
// @RestController
public class ApiEventController {

	private final EventService eventService;

	@GetMapping("/events")
	public APIDataResponse<List<EventResponse>> getEvents(
		@Positive Long placeId,
		@Size(min = 2) String eventName,
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
	public APIDataResponse<String> createEvent(@Valid @RequestBody EventRequest eventRequest) {
		boolean result = eventService.createEvent(eventRequest.toDto());

		return APIDataResponse.of(Boolean.toString(result));
	}

	@GetMapping("/events/{eventId}")
	public APIDataResponse<EventResponse> getEvent(@Positive @PathVariable Long eventId) {
		EventResponse eventResponse = EventResponse.from(eventService.getEvent(eventId)
			.orElse(null));

		return APIDataResponse.of(eventResponse);
	}

	@PutMapping("/events/{eventId}")
	public APIDataResponse<String> modifyEvent(
		@Positive @PathVariable Long eventId,
		@Valid @RequestBody EventRequest eventRequest
	) {
		boolean result = eventService.modifyEvent(eventId, eventRequest.toDto());

		return APIDataResponse.of(Boolean.toString(result));
	}

	@DeleteMapping("/events/{eventId}")
	public APIDataResponse<String> removeEvent(@Positive @PathVariable Long eventId) {
		boolean result = eventService.removeEvent(eventId);

		return APIDataResponse.of(Boolean.toString(result));
	}

}
