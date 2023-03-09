package com.ykmxxi.aligong.controller.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ykmxxi.aligong.constant.ErrorCode;
import com.ykmxxi.aligong.constant.PlaceType;

@WebMvcTest(APIPlaceController.class)
class APIPlaceControllerTest {

	private final MockMvc mvc;

	public APIPlaceControllerTest(@Autowired MockMvc mvc) {
		this.mvc = mvc;
	}

	@DisplayName("[API][GET] 장소 리스트 조회")
	@Test
	void givenNothing_whenRequestingPlaces_thenReturnsListOfPlacesInStandardResponse() throws Exception {
		// Given

		// When & Then
		mvc.perform(get("/api/places"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
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

	@DisplayName("[API][GET] 장소 조회: 장소 존재")
	@Test
	void givenPlaceId_whenRequestingPlace_thenReturnsPlaceInStandardResponse() throws Exception {
		// Given
		int placeId = 1;

		// When & Then
		mvc.perform(get("/api/places/" + placeId))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
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

	@DisplayName("[API][GET] 장소 조회: 장소 없음")
	@Test
	void givenPlaceId_whenRequestingPlace_thenReturnsEmptyStandardResponse() throws Exception {
		// Given
		int placeId = 2;

		// When & Then
		mvc.perform(get("/api/places/" + placeId))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data").isEmpty())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
			.andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
	}
}