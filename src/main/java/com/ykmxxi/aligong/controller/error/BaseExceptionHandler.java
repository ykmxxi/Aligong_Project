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
	public ModelAndView general(GeneralException e) {
		ErrorCode errorCode = e.getErrorCode();

		return new ModelAndView(
			"error",
			Map.of(
				"statusCode", errorCode.getHttpStatus().value(),
				"errorCode", errorCode,
				"message", errorCode.getMessage()
			),
			errorCode.getHttpStatus()
		);
	}

	@ExceptionHandler
	public ModelAndView exception(Exception e) {
		ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;

		return new ModelAndView(
			"error",
			Map.of(
				"statusCode", errorCode.getHttpStatus().value(),
				"errorCode", errorCode,
				"message", errorCode.getMessage(e)
			),
			errorCode.getHttpStatus()
		);
	}

}
