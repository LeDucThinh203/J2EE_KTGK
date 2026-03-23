package com.example.demo.controller;

import com.example.demo.dto.StudentRegistrationForm;
import com.example.demo.entity.Role;
import com.example.demo.entity.Student;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.StudentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class RegistrationController {

	private final StudentRepository studentRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		if (!model.containsAttribute("registrationForm")) {
			model.addAttribute("registrationForm", new StudentRegistrationForm());
		}
		return "register";
	}

	@PostMapping("/register")
	public String registerStudent(
			@Valid @ModelAttribute("registrationForm") StudentRegistrationForm form,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes
	) {
		validateUniqueUsername(form, bindingResult);
		validateUniqueEmail(form, bindingResult);

		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registrationForm", bindingResult);
			redirectAttributes.addFlashAttribute("registrationForm", form);
			return "redirect:/register";
		}

		Role studentRole = roleRepository.findByName("STUDENT")
				.orElseGet(() -> roleRepository.save(Role.builder().name("STUDENT").build()));

		Set<Role> roles = new HashSet<>();
		roles.add(studentRole);

		Student student = Student.builder()
				.username(form.getUsername().trim())
				.password(passwordEncoder.encode(form.getPassword()))
				.email(form.getEmail().trim())
				.roles(roles)
				.build();

		studentRepository.save(student);
		redirectAttributes.addFlashAttribute("successMessage", "Đăng ký tài khoản thành công");
		return "redirect:/home";
	}

	private void validateUniqueUsername(StudentRegistrationForm form, BindingResult bindingResult) {
		String username = form.getUsername();
		if (username != null && studentRepository.findByUsername(username.trim()).isPresent()) {
			bindingResult.rejectValue("username", "username.exists", "Username đã tồn tại");
		}
	}

	private void validateUniqueEmail(StudentRegistrationForm form, BindingResult bindingResult) {
		String email = form.getEmail();
		if (email != null && studentRepository.findByEmail(email.trim()).isPresent()) {
			bindingResult.rejectValue("email", "email.exists", "Email đã tồn tại");
		}
	}
}