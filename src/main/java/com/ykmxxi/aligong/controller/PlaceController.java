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
import com.ykmxxi.aligong.domain.Place;
import com.ykmxxi.aligong.dto.PlaceResponse;
import com.ykmxxi.aligong.exception.GeneralException;
import com.ykmxxi.aligong.service.PlaceService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/places")
@Controller
public class PlaceController {

	private final PlaceService placeService;

	@GetMapping
	public ModelAndView places(@QuerydslPredicate(root = Place.class) Predicate predicate) {
		Map<String, Object> map = new HashMap<>();
		List<PlaceResponse> places = placeService.getPlaces(predicate)
			.stream()
			.map(PlaceResponse::from)
			.toList();
		map.put("places", places);

		return new ModelAndView("place/index", map);
	}

	@GetMapping("/{placeId}")
	public ModelAndView placeDetail(@PathVariable Long placeId) {
		Map<String, Object> map = new HashMap<>();
		PlaceResponse place = placeService.getPlace(placeId)
			.map(PlaceResponse::from)
			.orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));

		map.put("place", place);

		return new ModelAndView("place/detail", map);
	}

}
