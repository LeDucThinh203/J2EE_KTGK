package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class HomeController {
	private final CourseRepository courseRepository;
	private final EnrollmentRepository enrollmentRepository;

	@GetMapping({"/", "/home", "/courses"})
	public String home(
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "q", required = false) String query,
			Model model
	) {
		int pageIndex = Math.max(page, 0);
		Pageable pageable = PageRequest.of(pageIndex, 5, Sort.by(Sort.Direction.ASC, "id"));
		String keyword = query == null ? "" : query.trim();
		Page<Course> coursePage = keyword.isBlank()
				? courseRepository.findAll(pageable)
				: courseRepository.findByNameContainingIgnoreCase(keyword, pageable);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean canEnroll = false;
		Set<Long> enrolledCourseIds = new HashSet<>();
		if (authentication != null && authentication.isAuthenticated()) {
			canEnroll = authentication.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.anyMatch(authority -> "ROLE_STUDENT".equals(authority));

			if (canEnroll) {
				enrolledCourseIds.addAll(enrollmentRepository.findCourseIdsByStudentUsername(authentication.getName()));
			}
		}

		model.addAttribute("coursePage", coursePage);
		model.addAttribute("currentPage", pageIndex);
		model.addAttribute("totalPages", coursePage.getTotalPages());
		model.addAttribute("q", keyword);
		model.addAttribute("canEnroll", canEnroll);
		model.addAttribute("enrolledCourseIds", enrolledCourseIds);
		return "home";
	}
}
