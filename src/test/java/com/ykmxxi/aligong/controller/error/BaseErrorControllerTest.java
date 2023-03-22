package com.ykmxxi.aligong.controller.error;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@DisplayName("View 컨트롤러 - 에러")
@WebMvcTest(BaseErrorController.class)
class BaseErrorControllerTest {

	private final MockMvc mvc;

	public BaseErrorControllerTest(@Autowired MockMvc mvc) {
		this.mvc = mvc;
	}

	@DisplayName("[view][GET] 에러 페이지 - 페이지 없음")
	@Test
	void givenWrongURI_whenRequestingPage_thenReturns404ErrorPage() throws Exception {
		// Given

		// When & Then
		mvc.perform(get("/wrong-uri"))
			.andExpect(status().isNotFound())
			.andDo(print());
	}

}
