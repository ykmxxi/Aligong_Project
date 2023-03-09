package com.ykmxxi.aligong.controller.api;

import java.time.LocalDateTime;
import java.util.List;

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

@RequestMapping("/api")
@RestController
public class APIEventController {

	@GetMapping("/events")
	public APIDataResponse<List<EventResponse>> getEvents() {
		return APIDataResponse.of(List.of(EventResponse.of(
			1L,
			"오후 운동",
			EventStatus.OPENED,
			LocalDateTime.of(2023, 3, 9, 13, 0, 0),
			LocalDateTime.of(2023, 3, 9, 16, 0, 0),
			0,
			50,
			"마스크 착용 권장"
		)));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/events")
	public APIDataResponse<Void> createEvent(@RequestBody EventRequest eventRequest) {
		return APIDataResponse.empty();
	}

	@GetMapping("/events/{eventId}")
	public APIDataResponse<EventResponse> getEvent(@PathVariable Long eventId) {
		if (eventId.equals(2L)) {
			return APIDataResponse.empty();
		}

		return APIDataResponse.of(EventResponse.of(
			1L,
			"오후 운동",
			EventStatus.OPENED,
			LocalDateTime.of(2023, 3, 9, 13, 0, 0),
			LocalDateTime.of(2023, 3, 9, 16, 0, 0),
			0,
			50,
			"마스크 착용 권장"
		));
	}

	@PutMapping("/events/{eventId}")
	public APIDataResponse<Void> modifyEvent(
		@PathVariable Long eventId,
		@RequestBody EventRequest eventRequest
	) {
		return APIDataResponse.empty();
	}

	@DeleteMapping("/events/{eventId}")
	public APIDataResponse<Void> removeEvent(@PathVariable Long eventId) {
		return APIDataResponse.empty();
	}

}
