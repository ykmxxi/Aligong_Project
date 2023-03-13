package com.ykmxxi.aligong.controller.error;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

import com.ykmxxi.aligong.constant.ErrorCode;
import com.ykmxxi.aligong.dto.APIErrorResponse;
import com.ykmxxi.aligong.exception.GeneralException;

class APIExceptionHandlerTest {

	private APIExceptionHandler sut;
	// WebRequest 는 테스트에서 확인하고자 하는 부분이 아니기 때문에 환경으로 생각해 테스트 메서드 밖에 존재
	private WebRequest webRequest;

	@BeforeEach
	void beforeEach() {
		sut = new APIExceptionHandler();
		webRequest = new DispatcherServletWebRequest(new MockHttpServletRequest());
	}

	@DisplayName("검증 오류: 응답 데이터 정의")
	@Test
	void givenValidationException_whenCallingValidation_thenReturnsResponseEntity() {
		// given
		ConstraintViolationException e = new ConstraintViolationException(Set.of());

		// when
		ResponseEntity<Object> response = sut.validation(e, webRequest);

		// then
		assertThat(response)
			.hasFieldOrPropertyWithValue("body", APIErrorResponse.of(false, ErrorCode.VALIDATION_ERROR, e))
			.hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
			.hasFieldOrPropertyWithValue("statusCode", HttpStatus.BAD_REQUEST);

	}

	@DisplayName("일반 오류: 응답 데이터 정의")
	@Test
	void givenGeneralException_whenCallingValidation_thenReturnsResponseEntity() {
		// given
		ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
		GeneralException e = new GeneralException("This is test message.");

		// when
		ResponseEntity<Object> response = sut.general(e, webRequest);

		// then
		assertThat(response)
			.hasFieldOrPropertyWithValue("body", APIErrorResponse.of(false, errorCode, e))
			.hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
			.hasFieldOrPropertyWithValue("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@DisplayName("기타 오류: 응답 데이터 정의")
	@Test
	void givenOtherException_whenCallingValidation_thenReturnsResponseEntity() {
		// given
		Exception e = new Exception();

		// when
		ResponseEntity<Object> response = sut.exception(e, webRequest);

		// then
		assertThat(response)
			.hasFieldOrPropertyWithValue("body", APIErrorResponse.of(false, ErrorCode.INTERNAL_ERROR, e))
			.hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
			.hasFieldOrPropertyWithValue("statusCode", HttpStatus.INTERNAL_SERVER_ERROR);

	}

}