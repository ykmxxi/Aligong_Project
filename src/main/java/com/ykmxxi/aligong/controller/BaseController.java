package com.ykmxxi.aligong.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController implements ErrorController {

	/**
	 * 메인 페이지(root path) 를 돌려주는 메서드
	 * index 에 해당하는 뷰를 찾아 렌더링해서 보여줌
	 * @return 메인 페이지(root)의 인덱스
	 */
	@GetMapping("/")
	public String root() {
		return "index";
	}

	@RequestMapping("/error")
	public String error() {
		return "error";
	}
}
