package com.rdewispelaere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.rdewispelaere.model.User;
import com.rdewispelaere.service.UserService;

@Controller
public class UserController {

	private static final String PAGE_LOGIN = "login";
	private static final String PAGE_REGISTER = "register";
	private static final String PAGE_ERROR_500 = "500";

	@Autowired
	private UserService userService;

	@ModelAttribute
	public User initUser() {
		return new User();
	}

	@GetMapping({"/", "/Login"})
	public String getLogin(Model model) {
		return PAGE_LOGIN;
	}
	
	@GetMapping("/Register")
	public String getRegister(Model model) {
		return PAGE_REGISTER;
	}
	
	@PostMapping("/Register")
	public String postRegister(@ModelAttribute("user")User user, BindingResult result, Model mpdel) {
		
		if(result.hasErrors()) {
			//TODO
			return PAGE_ERROR_500;
		}
		
		userService.registerUser(user);
		
		return "redirect:/";
	}
}
