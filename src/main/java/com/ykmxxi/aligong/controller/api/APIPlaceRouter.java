package com.ykmxxi.aligong.controller.api;

import static org.springframework.web.servlet.function.RequestPredicates.*;
import static org.springframework.web.servlet.function.RouterFunctions.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class APIPlaceRouter {

	@Bean
	public RouterFunction<ServerResponse> placeRouter(APIPlaceHandler placeHandler) {
		return route().nest(path("api/places"), builder -> builder
			.GET("", placeHandler::getPlaces)
			.POST("", placeHandler::createPlaces)
			.GET("/{placeId}", placeHandler::getPlace)
			.PUT("/{placeId}", placeHandler::modifyPlace)
			.DELETE("/{placeId}", placeHandler::removePlace)
		).build();
	}
}
