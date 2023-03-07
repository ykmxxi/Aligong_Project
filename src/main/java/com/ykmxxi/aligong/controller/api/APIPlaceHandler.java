package com.ykmxxi.aligong.controller.api;

import static org.springframework.web.servlet.function.ServerResponse.*;

import java.net.URI;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Component
public class APIPlaceHandler {

	public ServerResponse getPlaces(ServerRequest request) {
		return ok().body(List.of("place1", "place2"));
	}

	public ServerResponse createPlaces(ServerRequest request) {
		return created(URI.create("/api/places/1")).body(true); // TODO: URI 의 1은 서비스 로직 구현시 수정해야 함
	}

	public ServerResponse getPlace(ServerRequest request) {
		return ok().body("place" + request.pathVariable("placeId"));
	}

	public ServerResponse modifyPlace(ServerRequest request) {
		return ok().body(true);
	}

	public ServerResponse removePlace(ServerRequest request) {
		return ok().body(true);
	}

}
