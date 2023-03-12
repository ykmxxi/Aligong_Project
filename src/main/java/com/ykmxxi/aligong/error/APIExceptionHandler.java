package com.ykmxxi.aligong.error;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ykmxxi.aligong.constant.ErrorCode;
import com.ykmxxi.aligong.dto.APIErrorResponse;
import com.ykmxxi.aligong.exception.GeneralException;

@RestControllerAdvice(annotations = RestController.class) // api 만 적용, view 에 영향 X
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
		ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
		HttpStatus status = HttpStatus.BAD_REQUEST;

		return super.handleExceptionInternal(
			e,
			APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(e)),
			HttpHeaders.EMPTY,
			status,
			request
		);
	}

	@ExceptionHandler
	public ResponseEntity<Object> general(GeneralException e, WebRequest request) {
		ErrorCode errorCode = e.getErrorCode();
		HttpStatus status = errorCode.isClientSideError() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;

		return callSuperInternalExceptionHandler(e, errorCode, HttpHeaders.EMPTY, status, request);
	}

	@ExceptionHandler
	public ResponseEntity<Object> exception(Exception e, WebRequest request) {
		ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

		return callSuperInternalExceptionHandler(e, errorCode, HttpHeaders.EMPTY, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		ErrorCode errorCode = status.is4xxClientError() ?
			ErrorCode.SPRING_BAD_REQUEST :
			ErrorCode.SPRING_INTERNAL_ERROR;

		return callSuperInternalExceptionHandler(ex, errorCode, headers, status, request);
	}

	private ResponseEntity<Object> callSuperInternalExceptionHandler(Exception ex, ErrorCode errorCode,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		return super.handleExceptionInternal(
			ex,
			APIErrorResponse.of(false, errorCode.getCode(), errorCode.getMessage(ex)),
			headers,
			status,
			request
		);
	}
}
