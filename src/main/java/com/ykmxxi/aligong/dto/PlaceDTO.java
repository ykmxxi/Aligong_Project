package com.ykmxxi.aligong.dto;

import java.time.LocalDateTime;

import com.ykmxxi.aligong.constant.PlaceType;

public record PlaceDTO(
	PlaceType placeType,
	String placeName,
	String address,
	String phoneNumber,
	Integer capacity,
	String memo,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
	public static PlaceDTO of(
		PlaceType placeType,
		String placeName,
		String address,
		String phoneNumber,
		Integer capacity,
		String memo,
		LocalDateTime createdAt,
		LocalDateTime modifiedAt
	) {
		return new PlaceDTO(
			placeType,
			placeName,
			address,
			phoneNumber,
			capacity,
			memo,
			createdAt,
			modifiedAt
		);
	}
}