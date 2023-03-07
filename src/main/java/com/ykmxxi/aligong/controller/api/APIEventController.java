package com.ykmxxi.aligong.controller.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class APIEventController {

	@GetMapping("/events")
	public List<String> getEvents() {
		return List.of("event1", "event2");
	}

	@PostMapping("/events")
	public Boolean createEvent() {
		return true;
	}

	@GetMapping("/events/{eventId}")
	public String getEvent(@PathVariable Integer eventId) {
		return "event " + eventId;
	}

	@PutMapping("/events/{eventId}")
	public Boolean modifyEvent(@PathVariable Integer eventId) {
		return true;
	}

	@DeleteMapping("/events/{eventId}")
	public Boolean removeEvent(@PathVariable Integer eventId) {
		return true;
	}
}
