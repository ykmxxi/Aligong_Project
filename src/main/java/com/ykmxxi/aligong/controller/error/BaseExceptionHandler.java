package com.ykmxxi.aligong.controller.error;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.ykmxxi.aligong.constant.ErrorCode;
import com.ykmxxi.aligong.exception.GeneralException;

@ControllerAdvice
public class BaseExceptionHandler {

	@ExceptionHandler
	public ModelAndView general(GeneralException e) { // 커스텀 예외
		ErrorCode errorCode = e.getErrorCode();
		HttpStatus status = errorCode.isClientSideError() ?
			HttpStatus.BAD_REQUEST :
			HttpStatus.INTERNAL_SERVER_ERROR;

		return new ModelAndView(
			"error",
			Map.of(
				"statusCode", status.value(),
				"errorCode", errorCode,
				"message", errorCode.getMessage(e)
			),
			status
		);
	}

	@ExceptionHandler
	public ModelAndView exception(Exception e) { // 일반 예외
		ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

		return new ModelAndView(
			"error",
			Map.of(
				"statusCode", status.value(),
				"errorCode", errorCode,
				"message", errorCode.getMessage(e)
			),
			status
		);
	}
}
