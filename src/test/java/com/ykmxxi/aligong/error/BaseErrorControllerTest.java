package com.ykmxxi.aligong.error;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BaseErrorController.class)
class BaseErrorControllerTest {

	private final MockMvc mvc;

	public BaseErrorControllerTest(@Autowired MockMvc mvc) {
		this.mvc = mvc;
	}

	@DisplayName("[view][GET] 에러 페이지")
	@Test
	void givenWrongURI_whenRequestingPage_thenReturns404ErrorPage() throws Exception {
		// given

		// when & then
		mvc.perform(get("/wrong-url"))
			.andExpect(status().isNotFound())
			.andDo(print());
	}

}