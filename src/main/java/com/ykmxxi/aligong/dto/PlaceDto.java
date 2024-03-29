package com.ykmxxi.aligong.dto;

import java.time.LocalDateTime;

import com.ykmxxi.aligong.constant.PlaceType;
import com.ykmxxi.aligong.domain.Place;

public record PlaceDto(
	Long id,
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
		Long id,
		PlaceType placeType,
		String placeName,
		String address,
		String phoneNumber,
		Integer capacity,
		String memo,
		LocalDateTime createdAt,
		LocalDateTime modifiedAt
	) {
		return new PlaceDto(id, placeType, placeName, address, phoneNumber, capacity, memo, createdAt, modifiedAt);
	}

	public static PlaceDto of(Place place) {
		return new PlaceDto(
			place.getId(),
			place.getPlaceType(),
			place.getPlaceName(),
			place.getAddress(),
			place.getPhoneNumber(),
			place.getCapacity(),
			place.getMemo(),
			place.getCreatedAt(),
			place.getModifiedAt()
		);
	}

	public Place toEntity() {
		return Place.of(placeType, placeName, address, phoneNumber, capacity, memo);
	}

	public Place updateEntity(Place place) {
		if (placeType != null) {
			place.setPlaceType(placeType);
		}
		if (placeName != null) {
			place.setPlaceName(placeName);
		}
		if (address != null) {
			place.setAddress(address);
		}
		if (phoneNumber != null) {
			place.setPhoneNumber(phoneNumber);
		}
		if (capacity != null) {
			place.setCapacity(capacity);
		}
		if (memo != null) {
			place.setMemo(memo);
		}

		return place;
	}

}
