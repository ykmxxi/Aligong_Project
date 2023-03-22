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

@DisplayName("View 컨트롤러 - 기본 페이지")
@WebMvcTest(BaseController.class)
class BaseControllerTest {

	private final MockMvc mvc;

	public BaseControllerTest(@Autowired MockMvc mvc) {
		this.mvc = mvc;
	}

	@DisplayName("[view][GET] 기본 페이지 요청")
	@Test
	void givenNothing_whenRequestingRootPage_thenReturnsIndexPage() throws Exception {
		// Given

		// When & Then
		mvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andExpect(content().string(containsString("This is default page.")))
			.andExpect(view().name("index"))
			.andDo(print());
	}

}
