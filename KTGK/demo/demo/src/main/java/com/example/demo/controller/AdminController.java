package com.example.demo.controller;

import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;
	private final EnrollmentRepository enrollmentRepository;

	@GetMapping("/admin")
	public String adminDashboard(Model model) {
		model.addAttribute("studentCount", studentRepository.count());
		model.addAttribute("courseCount", courseRepository.count());
		model.addAttribute("enrollmentCount", enrollmentRepository.count());
		return "admin/index";
	}
}