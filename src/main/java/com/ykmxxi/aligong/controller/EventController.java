package com.ykmxxi.aligong.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.querydsl.core.types.Predicate;
import com.ykmxxi.aligong.constant.ErrorCode;
import com.ykmxxi.aligong.domain.Event;
import com.ykmxxi.aligong.dto.EventResponse;
import com.ykmxxi.aligong.exception.GeneralException;
import com.ykmxxi.aligong.service.EventService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/events")
@Controller
public class EventController {

	private final EventService eventService;

	@GetMapping
	public ModelAndView events(@QuerydslPredicate(root = Event.class) Predicate predicate) {
		Map<String, Object> map = new HashMap<>();
		List<EventResponse> events = eventService.getEvents(predicate)
			.stream()
			.map(EventResponse::from)
			.toList();

		map.put("events", events);

		return new ModelAndView("event/index", map);
	}

	@GetMapping("/{eventId}")
	public ModelAndView eventDetail(@PathVariable Long eventId) {
		Map<String, Object> map = new HashMap<>();
		EventResponse event = eventService.getEvent(eventId)
			.map(EventResponse::from)
			.orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));

		map.put("event", event);

		return new ModelAndView("event/detail", map);
	}

}
