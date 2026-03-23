package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
	@GetMapping("/login")
	public String login(
			@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout,
			Model model
	) {
		if (error != null) {
			model.addAttribute("errorMessage", "Username hoặc password không đúng");
		}

		if (logout != null) {
			model.addAttribute("successMessage", "Đăng xuất thành công");
		}

		return "login";
	}
}