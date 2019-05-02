package com.rdewispelaere.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.rdewispelaere.dao.DAOException;
import com.rdewispelaere.model.User;
import com.rdewispelaere.service.UserService;

@Controller
public class UserController {

	private static final String PAGE_LOGIN = "login";

	private static final Logger logger = Logger.getLogger(UserController.class);

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

	@PostMapping({"/", "/Login"})
	public String postLogin(@ModelAttribute("user")User user, BindingResult result) {

		try {
			UserDetails userDetails = this.userService.loadUserByUsername(user.getName());
			if(userDetails.getPassword().equals(user.getPassword())) {

			}
			else {

			}
			
		} catch (DAOException e) {
			logger.warn("");
		}
		return "redirect:/";
	}
}
