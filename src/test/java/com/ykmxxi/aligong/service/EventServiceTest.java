package com.ykmxxi.aligong.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ykmxxi.aligong.constant.ErrorCode;
import com.ykmxxi.aligong.constant.EventStatus;
import com.ykmxxi.aligong.dto.EventDto;
import com.ykmxxi.aligong.exception.GeneralException;
import com.ykmxxi.aligong.repository.EventRepository;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

	@InjectMocks
	private EventService sut;
	@Mock
	private EventRepository eventRepository;

	@DisplayName("검색 조건 없이 이벤트 검색 시 전체 이벤트 리스트를 출력")
	@Test
	void givenNothing_whenSearchingEvents_thenReturnsEntireEventList() {
		// given
		given(eventRepository.findEvents(null, null, null, null, null))
			.willReturn(List.of(
				createEventDTO(1L, "오전 운동", true),
				createEventDTO(1L, "오후 운동", false)
			));

		// when
		List<EventDto> list = sut.getEvents(null, null, null, null, null);

		// then
		assertThat(list).hasSize(2);
		// verify(eventRepository).findEvents(null, null, null, null, null);
		then(eventRepository).should().findEvents(null, null, null, null, null);
	}

	@DisplayName("검색 조건을 넣어 이벤트 검색 시 검색된 이벤트 결과를 출력")
	@Test
	void givenSearchParams_whenSearchingEvents_thenReturnsEventList() {
		// given
		long placeId = 1L;
		String eventName = "오전 운동";
		EventStatus eventStatus = EventStatus.OPENED;
		LocalDateTime eventStartDatetime = LocalDateTime.of(2023, 3, 9, 10, 0, 0);
		LocalDateTime eventEndDatetime = LocalDateTime.of(2023, 3, 9, 11, 0, 0);

		given(eventRepository.findEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime))
			.willReturn(List.of(
				createEventDTO(1L, "오전 운동", eventStatus, eventStartDatetime, eventEndDatetime)
			));

		// when
		List<EventDto> list = sut.getEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime);

		// then
		assertThat(list)
			.hasSize(1)
			.allSatisfy(event -> {
					assertThat(event)
						.hasFieldOrPropertyWithValue("placeId", placeId)
						.hasFieldOrPropertyWithValue("eventName", eventName)
						.hasFieldOrPropertyWithValue("eventStatus", eventStatus);
					assertThat(event.eventStartDatetime()).isAfterOrEqualTo(eventStartDatetime);
					assertThat(event.eventStartDatetime()).isBeforeOrEqualTo(eventEndDatetime);
				}
			);

		// verify(eventRepository).findEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime);
		then(eventRepository).should()
			.findEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime);
	}

	@DisplayName("이벤트 검색 시 에러가 발생하는 경우, general 에러로 전환하여 예외 던진다.")
	@Test
	void givenDataRelatedException_whenSearchingEvents_thenReturnsThrowsGeneralException() {
		// given
		Exception e = new RuntimeException("This is test message.");
		given(eventRepository.findEvents(any(), any(), any(), any(), any()))
			.willThrow(e);

		// when
		Throwable thrown = catchThrowable(() -> sut.getEvents(null, null, null, null, null));

		// then
		assertThat(thrown)
			.isInstanceOf(GeneralException.class)
			.hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
		// verify(eventRepository).findEvents(null, null, null, null, null);
		then(eventRepository).should().findEvents(any(), any(), any(), any(), any());
	}

	@DisplayName("이벤트 ID로 존재하는 이벤트를 조회하면, 해당 이벤트 정보를 출력하여 보여준다.")
	@Test
	void givenEventId_whenSearchingExistingEvent_thenReturnsEvent() {
		// given
		long eventId = 1L;
		EventDto eventDTO = createEventDTO(1L, "오전 운동", true);
		given(eventRepository.findEvent(eventId)).willReturn(Optional.of(eventDTO));

		// when
		Optional<EventDto> result = sut.getEvent(eventId);

		// then
		assertThat(result).hasValue(eventDTO);
		then(eventRepository).should().findEvent(eventId);
	}

	@DisplayName("이벤트 ID로 이벤트를 조회하면, 빈 정보를 출력하여 보여준다.")
	@Test
	void givenEventId_whenSearchingNonexistentEvent_thenReturnsEmptyOptional() {
		// given
		long eventId = 2L;
		given(eventRepository.findEvent(eventId)).willReturn(Optional.empty());

		// when
		Optional<EventDto> result = sut.getEvent(eventId);

		// then
		assertThat(result).isEmpty();
		then(eventRepository).should().findEvent(eventId);
	}

	@DisplayName("이벤트 ID로 이벤트를 조회하는데 데이터 관련 에러가 발생한 경우, general 에러로 전환하여 예외 던진다.")
	@Test
	void givenDataRelatedException_whenSearchingEvent_thenThrowsGeneralException() {
		// given
		RuntimeException e = new RuntimeException("This is test.");
		given(eventRepository.findEvent(any())).willThrow(e);

		// when
		Throwable thrown = catchThrowable(() -> sut.getEvent(null));

		// then
		assertThat(thrown)
			.isInstanceOf(GeneralException.class)
			.hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
		then(eventRepository).should().findEvent(any());
	}

	@DisplayName("이벤트 정보를 주면, 이벤트를 생성하고 결과를 true 로 보여준다.")
	@Test
	void givenEvent_whenCreating_thenCreatesEventAndReturnsTrue() {
		// given
		EventDto dto = createEventDTO(1L, "오후 운동", false);
		given(eventRepository.insertEvent(dto)).willReturn(true);

		// when
		boolean result = sut.createEvent(dto);

		// then
		assertThat(result).isTrue();
		then(eventRepository).should().insertEvent(dto);
	}

	@DisplayName("이벤트 정보를 주지 않으면, 생성 중단하고 결과를 false 로 보여준다.")
	@Test
	void givenNothing_whenCreating_thenAbortCreatingAndReturnsFalse() {
		// given
		given(eventRepository.insertEvent(null)).willReturn(false);

		// when
		boolean result = sut.createEvent(null);

		// then
		assertThat(result).isFalse();
		verify(eventRepository).insertEvent(null);
	}

	@DisplayName("이벤트 생성 중 데이터 예외가 발생하면, general 에러로 전환하여 예외 던진다.")
	@Test
	void givenDataRelatedException_whenCreating_thenThrowsGeneralException() {
		// given
		RuntimeException e = new RuntimeException("This is test.");
		given(eventRepository.insertEvent(any())).willThrow(e);

		// when
		Throwable thrown = catchThrowable(() -> sut.createEvent(null));

		// then
		assertThat(thrown)
			.isInstanceOf(GeneralException.class)
			.hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
		then(eventRepository).should().insertEvent(any());
	}

	@DisplayName("이벤트 ID와 정보를 주면, 이벤트 정보를 변경하고 결과를 true 로 보여준다.")
	@Test
	void givenEventIdAndItsInfo_whenModifying_thenModifiesEventAndReturnsTrue() {
		// given
		long eventId = 1L;
		EventDto dto = createEventDTO(1L, "오후 운동", false);
		given(eventRepository.updateEvent(eventId, dto)).willReturn(true);

		// when
		boolean result = sut.modifyEvent(eventId, dto);

		// then
		assertThat(result).isTrue();
		then(eventRepository).should().updateEvent(eventId, dto);
	}

	@DisplayName("이벤트 ID를 주지 않으면, 이벤트 정보 변경 중단하고 결과를 false 로 보여준다.")
	@Test
	void givenNoEventId_whenModifying_thenAbortModifyingAndReturnsFalse() {
		// given
		EventDto dto = createEventDTO(1L, "오후 운동", false);
		given(eventRepository.updateEvent(null, dto)).willReturn(false);

		// when
		boolean result = sut.modifyEvent(null, dto);

		// then
		assertThat(result).isFalse();
		then(eventRepository).should().updateEvent(null, dto);
	}

	@DisplayName("이벤트 ID만 주고 변경할 정보를 주지 않으면, 이벤트 정보 변경 중단하고 결과를 false 로 보여준다.")
	@Test
	void givenEventIdOnly_whenModifying_thenAbortModifyingAndReturnsFalse() {
		// given
		long eventId = 1L;
		given(eventRepository.updateEvent(eventId, null)).willReturn(false);

		// when
		boolean result = sut.modifyEvent(eventId, null);

		// then
		assertThat(result).isFalse();
		then(eventRepository).should().updateEvent(eventId, null);
	}

	@DisplayName("이벤트 변경 중 데이터 오류가 발생하면, general 에러로 전환하여 예외 던진다.")
	@Test
	void givenDataRelatedException_whenModifying_thenThrowsGeneralException() {
		// given
		RuntimeException e = new RuntimeException("This is test.");
		given(eventRepository.updateEvent(any(), any())).willThrow(e);

		// when
		Throwable thrown = catchThrowable(() -> sut.modifyEvent(null, null));

		// then
		assertThat(thrown)
			.isInstanceOf(GeneralException.class)
			.hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
		then(eventRepository).should().updateEvent(any(), any());
	}

	@DisplayName("이벤트 ID를 주면, 이벤트 정보를 삭제하고 결과를 true 로 보여준다.")
	@Test
	void givenEventId_whenDeleting_thenDeletesEventAndReturnsTrue() {
		// given
		long eventId = 1L;
		given(eventRepository.deleteEvent(eventId)).willReturn(true);

		// when
		boolean result = sut.removeEvent(eventId);

		// then
		assertThat(result).isTrue();
		then(eventRepository).should().deleteEvent(eventId);
	}

	@DisplayName("이벤트 ID를 주지 않으면, 삭제 중단하고 결과를 false 로 보여준다.")
	@Test
	void givenNothing_whenDeleting_thenAbortsDeletingAndReturnsFalse() {
		// given
		given(eventRepository.deleteEvent(null)).willReturn(false);

		// when
		boolean result = sut.removeEvent(null);

		// then
		assertThat(result).isFalse();
		then(eventRepository).should().deleteEvent(null);
	}

	@DisplayName("이벤트 삭제 중 데이터 오류가 발생하면, general 에러로 전환하여 예외 던진다.")
	@Test
	void givenDataRelatedException_whenDeleting_thenThrowsGeneralException() {
		// given
		RuntimeException e = new RuntimeException("This is test.");
		given(eventRepository.deleteEvent(any())).willThrow(e);

		// when
		Throwable thrown = catchThrowable(() -> sut.removeEvent(null));

		// then
		assertThat(thrown)
			.isInstanceOf(GeneralException.class)
			.hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
		then(eventRepository).should().deleteEvent(any());
	}

	private EventDto createEventDTO(long placeId, String eventName, boolean isMorning) {
		String hourStart = isMorning ? "09" : "13";
		String hourEnd = isMorning ? "12" : "16";

		return createEventDTO(
			placeId,
			eventName,
			EventStatus.OPENED,
			LocalDateTime.parse("2023-03-09T%s:00:00".formatted(hourStart)),
			LocalDateTime.parse("2023-03-09T%s:00:00".formatted(hourEnd))
		);
	}

	private EventDto createEventDTO(
		long placeId,
		String eventName,
		EventStatus eventStatus,
		LocalDateTime eventStartDateTime,
		LocalDateTime eventEndDateTime
	) {
		return EventDto.of(
			1L,
			placeId,
			eventName,
			eventStatus,
			eventStartDateTime,
			eventEndDateTime,
			0,
			50,
			"마스크 착용 권장",
			LocalDateTime.now(),
			LocalDateTime.now()
		);
	}
}