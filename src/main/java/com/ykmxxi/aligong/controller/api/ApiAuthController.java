package com.ykmxxi.aligong.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ykmxxi.aligong.dto.APIDataResponse;
import com.ykmxxi.aligong.dto.AdminRequest;
import com.ykmxxi.aligong.dto.LoginRequest;


@Deprecated
// @RequestMapping("/api")
// @RestController
public class ApiAuthController {

	@PostMapping("/sign-up")
	public APIDataResponse<String> signUp(@RequestBody AdminRequest adminRequest) {
		return APIDataResponse.empty();
	}

	@PostMapping("/login")
	public APIDataResponse<String> login(@RequestBody LoginRequest loginRequest) {
		return APIDataResponse.empty();
	}
}
