package com.ykmxxi.aligong.controller.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ykmxxi.aligong.constant.ErrorCode;
import com.ykmxxi.aligong.constant.PlaceType;
import com.ykmxxi.aligong.dto.PlaceRequest;

@Deprecated
@Disabled("API 컨트롤러 비활성화")
@WebMvcTest(ApiPlaceController.class)
class ApiPlaceControllerTest {

	private final MockMvc mvc;
	private final ObjectMapper mapper;

	public ApiPlaceControllerTest(
		@Autowired MockMvc mvc,
		@Autowired ObjectMapper mapper
	) {
		this.mvc = mvc;
		this.mapper = mapper;
	}

	@DisplayName("[API][GET] 장소 리스트 조회")
	@Test
	void givenNothing_whenRequestingPlaces_thenReturnsPlacesInStandardResponse() throws Exception {
		// Given

		// When & Then
		mvc.perform(get("/api/places"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data").isArray())
			.andExpect(jsonPath("$.data[0].placeType").value(PlaceType.SPORTS.name()))
			.andExpect(jsonPath("$.data[0].placeName").value("치평테니스코트"))
			.andExpect(jsonPath("$.data[0].address").value("광주광역시 서구 시청로 11"))
			.andExpect(jsonPath("$.data[0].phoneNumber").value("010-1234-4321"))
			.andExpect(jsonPath("$.data[0].capacity").value(50))
			.andExpect(jsonPath("$.data[0].memo").value("새시설"))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
			.andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
	}

	@DisplayName("[API][POST] 장소 생성")
	@Test
	void givenPlace_whenCreatingAPlace_thenReturnsSuccessfulStandardResponse() throws Exception {
		// Given
		PlaceRequest placeRequest = PlaceRequest.of(
			PlaceType.COMMON,
			"치평테니스코트",
			"광주광역시 서구 시청로 11",
			"010-1234-4321",
			50,
			"새시설"
		);

		// When & Then
		mvc.perform(
				post("/api/places")
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(placeRequest))
			)
			.andExpect(status().isCreated())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
			.andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
	}

	@DisplayName("[API][GET] 단일 장소 조회 - 장소 있는 경우, 장소 데이터를 담은 표준 API 출력")
	@Test
	void givenPlaceId_whenRequestingExistentPlace_thenReturnsPlaceInStandardResponse() throws Exception {
		// Given
		long placeId = 1L;

		// When & Then
		mvc.perform(get("/api/places/" + placeId))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data").isMap())
			.andExpect(jsonPath("$.data.placeType").value(PlaceType.SPORTS.name()))
			.andExpect(jsonPath("$.data.placeName").value("치평테니스코트"))
			.andExpect(jsonPath("$.data.address").value("광주광역시 서구 시청로 11"))
			.andExpect(jsonPath("$.data.phoneNumber").value("010-1234-4321"))
			.andExpect(jsonPath("$.data.capacity").value(50))
			.andExpect(jsonPath("$.data.memo").value("새시설"))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
			.andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
	}

	@DisplayName("[API][GET] 단일 장소 조회 - 장소 없는 경우, 빈 표준 API 출력")
	@Test
	void givenPlaceId_whenRequestingNonexistentPlace_thenReturnsEmptyStandardResponse() throws Exception {
		// Given
		long placeId = 2L;

		// When & Then
		mvc.perform(get("/api/places/" + placeId))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data").isEmpty())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
			.andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
	}

	@DisplayName("[API][PUT] 장소 변경")
	@Test
	void givenPlace_whenModifyingAPlace_thenReturnsSuccessfulStandardResponse() throws Exception {
		// Given
		long placeId = 1L;
		PlaceRequest placeRequest = PlaceRequest.of(
			PlaceType.COMMON,
			"치평테니스코트",
			"광주광역시 서구 시청로 11",
			"010-1234-4321",
			50,
			"새시설"
		);

		// When & Then
		mvc.perform(
				put("/api/places/" + placeId)
					.contentType(MediaType.APPLICATION_JSON)
					.content(mapper.writeValueAsString(placeRequest))
			)
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
			.andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
	}

	@DisplayName("[API][DELETE] 장소 삭제")
	@Test
	void givenPlace_whenDeletingAPlace_thenReturnsSuccessfulStandardResponse() throws Exception {
		// Given
		long placeId = 1L;

		// When & Then
		mvc.perform(delete("/api/places/" + placeId))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
			.andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
	}
}