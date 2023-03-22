package com.ykmxxi.aligong.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ykmxxi.aligong.dto.AdminRequest;
import com.ykmxxi.aligong.dto.ApiDataResponse;
import com.ykmxxi.aligong.dto.LoginRequest;

@Deprecated
// @RequestMapping("/api")
// @RestController
public class ApiAuthController {

	@PostMapping("/sign-up")
	public ApiDataResponse<String> signUp(@RequestBody AdminRequest adminRequest) {
		return ApiDataResponse.empty();
	}

	@PostMapping("/login")
	public ApiDataResponse<String> login(@RequestBody LoginRequest loginRequest) {
		return ApiDataResponse.empty();
	}

}
