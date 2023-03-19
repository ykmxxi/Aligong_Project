package com.ykmxxi.aligong.controller.api;

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

import com.ykmxxi.aligong.constant.PlaceType;
import com.ykmxxi.aligong.dto.APIDataResponse;
import com.ykmxxi.aligong.dto.PlaceRequest;
import com.ykmxxi.aligong.dto.PlaceResponse;

// @RequestMapping("/api")
// @RestController
public class APIPlaceController {

	@GetMapping("/places")
	public APIDataResponse<List<PlaceResponse>> getPlaces() {
		return APIDataResponse.of(List.of(PlaceResponse.of(
			PlaceType.SPORTS,
			"치평테니스코트",
			"광주광역시 서구 시청로 11",
			"010-1234-4321",
			50,
			"새시설"
		)));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/places")
	public APIDataResponse<Void> createPlace(@RequestBody PlaceRequest placeRequest) {
		return APIDataResponse.empty();
	}

	@GetMapping("/places/{placeId}")
	public APIDataResponse<PlaceResponse> getPlace(@PathVariable Integer placeId) {
		if (placeId.equals(2)) {
			return APIDataResponse.empty();
		}

		return APIDataResponse.of(PlaceResponse.of(
			PlaceType.SPORTS,
			"치평테니스코트",
			"광주광역시 서구 시청로 11",
			"010-1234-4321",
			50,
			"새시설"
		));
	}

	@PutMapping("/places/{placeId}")
	public APIDataResponse<Void> modifyPlace(
		@PathVariable Long placeId,
		@RequestBody PlaceRequest placeRequest
	) {
		return APIDataResponse.empty();
	}

	@DeleteMapping("/places/{placeId}")
	public APIDataResponse<Void> removePlace(@PathVariable Long placeId) {
		return APIDataResponse.empty();
	}
}
