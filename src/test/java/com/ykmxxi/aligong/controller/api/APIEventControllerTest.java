package com.ykmxxi.aligong.controller.api;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ykmxxi.aligong.constant.ErrorCode;
import com.ykmxxi.aligong.constant.EventStatus;
import com.ykmxxi.aligong.dto.EventDto;
import com.ykmxxi.aligong.dto.EventResponse;
import com.ykmxxi.aligong.service.EventService;

@WebMvcTest(APIEventController.class)
class APIEventControllerTest {

	private final MockMvc mvc;
	private final ObjectMapper mapper;

	@MockBean
	private EventService eventService;

	public APIEventControllerTest(
		@Autowired MockMvc mvc,
		@Autowired ObjectMapper mapper
	) {
		this.mvc = mvc;
		this.mapper = mapper;
	}

	@DisplayName("[API][GET] 이벤트 리스트 조회")
	@Test
	void givenNothing_whenRequestingEvents_thenReturnsListOfEventsInStandardResponse() throws Exception {
		// Given
		given(eventService.getEvents(any(), any(), any(), any(), any())).willReturn(List.of(createEventDTO()));

		// When & Then
		mvc.perform(
				get("/api/events")
					.queryParam("placeId", "1")
					.queryParam("eventName", "운동")
					.queryParam("eventStatus", EventStatus.OPENED.name())
					.queryParam("eventStartDatetime", "2023-03-09T00:00:00")
					.queryParam("eventEndDatetime", "2023-03-09T00:00:00")
			)
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.data[0].placeId").value(1L))
			.andExpect(jsonPath("$.data[0].eventName").value("오후 운동"))
			.andExpect(jsonPath("$.data[0].eventStatus").value(EventStatus.OPENED.name()))
			.andExpect(jsonPath("$.data[0].eventStartDatetime").value(LocalDateTime
				.of(2023, 3, 9, 13, 0, 0)
				.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
			.andExpect(jsonPath("$.data[0].eventEndDatetime").value(LocalDateTime
				.of(2023, 3, 9, 16, 0, 0)
				.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
			.andExpect(jsonPath("$.data[0].currentNumberOfPeople").value(0))
			.andExpect(jsonPath("$.data[0].capacity").value(50))
			.andExpect(jsonPath("$.data[0].memo").value("마스크 착용 권장"))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
			.andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
		then(eventService).should().getEvents(any(), any(), any(), any(), any());
	}

	@DisplayName("[API][POST] 이벤트 생성")
	@Test
	void givenEvent_whenCreatingAnEvent_thenReturnsSuccessfulStandardResponse() throws Exception {
		// Given
		EventResponse eventResponse = EventResponse.of(
			1L,
			"오후 운동",
			EventStatus.OPENED,
			LocalDateTime.of(2023, 3, 9, 13, 0, 0),
			LocalDateTime.of(2023, 3, 9, 16, 0, 0),
			0,
			50,
			"마스크 착용 권장"
		);
		given(eventService.createEvent(any())).willReturn(true);

		// When & Then
		mvc.perform(
				post("/api/events")
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(eventResponse))
			)
			.andExpect(status().isCreated())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data").value(Boolean.TRUE.toString()))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
			.andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
		then(eventService).should().createEvent(any());
	}

	@DisplayName("[API][GET] 단일 이벤트 조회 - 이벤트 있는 경우, 이벤트 데이터를 담은 표준 API 출력")
	@Test
	void givenEventId_whenRequestingExistentEvent_thenReturnsEventInStandardResponse() throws Exception {
		// Given
		long eventId = 1L;
		given(eventService.getEvent(eventId)).willReturn(Optional.of(createEventDTO()));

		// When & Then
		mvc.perform(get("/api/events/" + eventId))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data").isMap())
			.andExpect(jsonPath("$.data.placeId").value(1L))
			.andExpect(jsonPath("$.data.eventName").value("오후 운동"))
			.andExpect(jsonPath("$.data.eventStatus").value(EventStatus.OPENED.name()))
			.andExpect(jsonPath("$.data.eventStartDatetime").value(LocalDateTime
				.of(2023, 3, 9, 13, 0, 0)
				.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
			.andExpect(jsonPath("$.data.eventEndDatetime").value(LocalDateTime
				.of(2023, 3, 9, 16, 0, 0)
				.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
			.andExpect(jsonPath("$.data.currentNumberOfPeople").value(0))
			.andExpect(jsonPath("$.data.capacity").value(50))
			.andExpect(jsonPath("$.data.memo").value("마스크 착용 권장"))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
			.andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
		then(eventService).should().getEvent(eventId);
	}

	@DisplayName("[API][GET] 단일 이벤트 조회 - 이벤트 없는 경우, 빈 표준 API 출력")
	@Test
	void givenEventId_whenRequestingNonexistentEvent_thenReturnsEmptyStandardResponse() throws Exception {
		// Given
		long eventId = 2L;
		given(eventService.getEvent(eventId)).willReturn(Optional.empty());

		// When & Then
		mvc.perform(get("/api/events/" + eventId))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data").isEmpty())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
			.andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
		then(eventService).should().getEvent(eventId);
	}

	@DisplayName("[API][PUT] 이벤트 변경")
	@Test
	void givenEvent_whenModifyingAnEvent_thenReturnsSuccessfulStandardResponse() throws Exception {
		// Given
		long eventId = 1L;
		EventResponse eventResponse = EventResponse.of(
			1L,
			"오후 운동",
			EventStatus.OPENED,
			LocalDateTime.of(2023, 3, 9, 13, 0, 0),
			LocalDateTime.of(2023, 3, 9, 16, 0, 0),
			0,
			50,
			"마스크 착용 권장"
		);
		given(eventService.modifyEvent(eq(eventId), any())).willReturn(true);

		// When & Then
		mvc.perform(
				put("/api/events/" + eventId)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(eventResponse))
			)
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data").value(Boolean.TRUE.toString()))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
			.andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
		then(eventService).should().modifyEvent(eq(eventId), any());
	}

	@DisplayName("[API][DELETE] 이벤트 삭제")
	@Test
	void givenEvent_whenDeletingAnEvent_thenReturnsSuccessfulStandardResponse() throws Exception {
		// Given
		long eventId = 1L;
		given(eventService.removeEvent(eventId)).willReturn(true);

		// When & Then
		mvc.perform(delete("/api/events/" + eventId))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data").value(Boolean.TRUE.toString()))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
			.andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
		then(eventService).should().removeEvent(eventId);
	}

	private EventDto createEventDTO() {
		return EventDto.of(
			1L,
			"오후 운동",
			EventStatus.OPENED,
			LocalDateTime.of(2023, 3, 9, 13, 0, 0),
			LocalDateTime.of(2023, 3, 9, 16, 0, 0),
			0,
			50,
			"마스크 착용 권장",
			LocalDateTime.now(),
			LocalDateTime.now()
		);
	}
}