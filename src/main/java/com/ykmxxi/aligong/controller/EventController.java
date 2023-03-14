package com.ykmxxi.aligong.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ykmxxi.aligong.constant.EventStatus;
import com.ykmxxi.aligong.dto.EventResponse;

@RequestMapping("/events")
@Controller
public class EventController {

	@GetMapping
	public ModelAndView events() {
		Map<String, Object> map = new HashMap<>();

		map.put("events", List.of(EventResponse.of(
			1L,
				1L,
				"오후 운동",
				EventStatus.OPENED,
				LocalDateTime.of(2023, 3, 14, 13, 0, 0),
				LocalDateTime.of(2023, 3, 14, 16, 0, 0),
				0,
				50,
				"마스크 착용 권장"
			), EventResponse.of(
				2L,
				1L,
				"오후 운동",
				EventStatus.OPENED,
				LocalDateTime.of(2023, 3, 14, 13, 0, 0),
				LocalDateTime.of(2023, 3, 14, 16, 0, 0),
				0,
				50,
				"마스크 착용 권장"
			)
		));

		return new ModelAndView("event/index", map);
	}

	@GetMapping("/{eventId}")
	public ModelAndView eventDetail(@PathVariable Long eventId) {
		Map<String, Object> map = new HashMap<>();

		map.put("events", EventResponse.of(
			1L,
			1L,
			"오후 운동",
			EventStatus.OPENED,
			LocalDateTime.of(2023, 3, 14, 13, 0, 0),
			LocalDateTime.of(2023, 3, 14, 16, 0, 0),
			0,
			50,
			"마스크 착용 권장"
			)
		);

		return new ModelAndView("event/detail", map);
	}
}
