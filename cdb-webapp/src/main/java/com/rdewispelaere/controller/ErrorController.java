package com.rdewispelaere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

	private static final String PAGE_ERROR_403 = "403";
	private static final String PAGE_ERROR_404 = "404";
	private static final String PAGE_ERROR_500 = "500";
	
	@GetMapping("/403")
	public String get403() {
		return PAGE_ERROR_403;
	}
	
	@GetMapping("/404")
	public String get404() {
		return PAGE_ERROR_404;
	}
	
	@GetMapping("/500")
	public String get500() {
		return PAGE_ERROR_500;
	}
}
