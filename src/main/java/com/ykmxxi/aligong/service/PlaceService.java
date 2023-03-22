package com.ykmxxi.aligong.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.querydsl.core.types.Predicate;
import com.ykmxxi.aligong.constant.ErrorCode;
import com.ykmxxi.aligong.dto.PlaceDto;
import com.ykmxxi.aligong.exception.GeneralException;
import com.ykmxxi.aligong.repository.PlaceRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlaceService {

	private final PlaceRepository placeRepository;

	public List<PlaceDto> getPlaces(Predicate predicate) {
		try {
			return StreamSupport.stream(placeRepository.findAll(predicate).spliterator(), false)
				.map(PlaceDto::of)
				.toList();
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}

	public Optional<PlaceDto> getPlace(Long placeId) {
		try {
			return placeRepository.findById(placeId).map(PlaceDto::of);
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}

	public boolean createPlace(PlaceDto placeDto) {
		try {
			if (placeDto == null) {
				return false;
			}

			placeRepository.save(placeDto.toEntity());
			return true;
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}

	public boolean modifyPlace(Long placeId, PlaceDto dto) {
		try {
			if (placeId == null || dto == null) {
				return false;
			}

			placeRepository.findById(placeId)
				.ifPresent(place -> placeRepository.save(dto.updateEntity(place)));

			return true;
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}

	public boolean removePlace(Long placeId) {
		try {
			if (placeId == null) {
				return false;
			}

			placeRepository.deleteById(placeId);
			return true;
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
		}
	}

}
