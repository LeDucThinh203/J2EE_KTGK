package com.example.demo.controller;

import com.example.demo.entity.Course;
import com.example.demo.entity.Enrollment;
import com.example.demo.entity.Student;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.EnrollmentRepository;
import com.example.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class EnrollmentController {

	private final EnrollmentRepository enrollmentRepository;
	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;

	@GetMapping("/my-courses")
	public String myCourses(org.springframework.ui.Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		List<Enrollment> enrollments = enrollmentRepository.findDetailedByStudentUsername(username);
		model.addAttribute("enrollments", enrollments);
		return "my-courses";
	}

	@GetMapping("/enroll/{courseId}")
	public String enrollCoursePage(@PathVariable("courseId") Long courseId, RedirectAttributes redirectAttributes) {
		if (!courseRepository.existsById(courseId)) {
			redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy học phần");
			return "redirect:/home";
		}

		redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng bấm nút Enroll trên trang danh sách học phần");
		return "redirect:/home";
	}

	@PostMapping("/enroll/{courseId}")
	public String enrollCourse(@PathVariable("courseId") Long courseId, RedirectAttributes redirectAttributes) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Student student = studentRepository.findByUsername(username).orElse(null);
		if (student == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy tài khoản đăng nhập");
			return "redirect:/home";
		}

		Course course = courseRepository.findById(courseId).orElse(null);
		if (course == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy học phần");
			return "redirect:/home";
		}

		if (enrollmentRepository.existsByStudent_UsernameAndCourse_Id(username, courseId)) {
			redirectAttributes.addFlashAttribute("errorMessage", "Bạn đã đăng ký học phần này rồi");
			return "redirect:/home";
		}

		Enrollment enrollment = Enrollment.builder()
				.student(student)
				.course(course)
				.enrollDate(LocalDate.now())
				.build();
		enrollmentRepository.save(enrollment);

		redirectAttributes.addFlashAttribute("successMessage", "Đăng ký học phần thành công");
		return "redirect:/my-courses";
	}
}