package com.ykmxxi.aligong.dto;

import java.time.LocalDateTime;

import com.ykmxxi.aligong.constant.PlaceType;

public record PlaceDto(
	PlaceType placeType,
	String placeName,
	String address,
	String phoneNumber,
	Integer capacity,
	String memo,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
	public static PlaceDto of(
		PlaceType placeType,
		String placeName,
		String address,
		String phoneNumber,
		Integer capacity,
		String memo,
		LocalDateTime createdAt,
		LocalDateTime modifiedAt
	) {
		return new PlaceDto(
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