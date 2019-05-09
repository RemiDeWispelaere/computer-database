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
	private static final String PAGE_REGISTER_ADMIN = "registerAdmin";
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
		
		userService.registerAsUser(user);
		
		return "redirect:/";
	}
	
	@GetMapping("/RegisterAdmin")
	public String getRegisterAdmin(Model model) {
		return PAGE_REGISTER_ADMIN;
	}
	
	@PostMapping("/RegisterAdmin")
	public String postRegisterAdmin(@ModelAttribute("user")User admin, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			//TODO
			return PAGE_ERROR_500;
		}
		
		userService.registerAsUser(admin);
		
		return "redirect:/ListComputer";
	}
}
