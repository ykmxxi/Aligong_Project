package com.ykmxxi.aligong.controller.error;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ykmxxi.aligong.constant.ErrorCode;
import com.ykmxxi.aligong.dto.ApiErrorResponse;

@Controller
public class BaseErrorController implements ErrorController {

	@RequestMapping(path = "/error", produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView errorHtml(HttpServletResponse response) {
		HttpStatus status = HttpStatus.valueOf(response.getStatus());
		ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;

		return new ModelAndView(
			"error",
			Map.of(
				"statusCode", status.value(),
				"errorCode", errorCode,
				"message", errorCode.getMessage(status.getReasonPhrase())
			),
			status
		);
	}

	@RequestMapping("/error")
	public ResponseEntity<ApiErrorResponse> error(HttpServletResponse response) {
		HttpStatus status = HttpStatus.valueOf(response.getStatus());
		ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;

		return ResponseEntity
			.status(status)
			.body(ApiErrorResponse.of(false, errorCode));
	}

}
