package com.ykmxxi.aligong.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BaseController.class)
class BaseControllerTest {

	private final MockMvc mvc;

	public BaseControllerTest(@Autowired MockMvc mvc) {
		this.mvc = mvc;
	}

	@DisplayName("[view][GET] 기본 페이지 요청")
	@Test
	void testRoot() throws Exception {
		// given

		// when & then
		mvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)) // contentTypeCompatibleWith(""): TEXT_HTML 이 포함되어 있으면 됨
			.andExpect(content().string(containsString("This is temporary root page...")))
			.andExpect(view().name("index"))
			.andDo(print());
	}
}