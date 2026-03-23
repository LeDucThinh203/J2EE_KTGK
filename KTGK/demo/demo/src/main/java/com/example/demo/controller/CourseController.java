package com.example.demo.controller;

import com.example.demo.dto.CourseForm;
import com.example.demo.entity.Category;
import com.example.demo.entity.Course;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.CourseRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CourseController {
	private final CourseRepository courseRepository;
	private final CategoryRepository categoryRepository;

	@GetMapping("/courses/new")
	public String showCreateForm(Model model) {
		model.addAttribute("mode", "create");
		model.addAttribute("courseId", null);
		model.addAttribute("courseForm", new CourseForm());
		model.addAttribute("categories", getAllCategories());
		return "course-form";
	}

	@PostMapping("/courses")
	public String createCourse(
			@Valid @ModelAttribute("courseForm") CourseForm form,
			BindingResult bindingResult,
			Model model,
			RedirectAttributes redirectAttributes
	) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("mode", "create");
			model.addAttribute("courseId", null);
			model.addAttribute("categories", getAllCategories());
			return "course-form";
		}

		Course course = new Course();
		applyForm(course, form);
		courseRepository.save(course);

		redirectAttributes.addFlashAttribute("successMessage", "Tạo học phần thành công");
		return "redirect:/home";
	}

	@GetMapping("/courses/{id}/edit")
	public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		Optional<Course> courseOpt = courseRepository.findById(id);
		if (courseOpt.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy học phần");
			return "redirect:/home";
		}

		Course course = courseOpt.get();
		CourseForm form = CourseForm.builder()
				.name(course.getName())
				.image(course.getImage())
				.credits(course.getCredits())
				.lecturer(course.getLecturer())
				.categoryId(course.getCategory() != null ? course.getCategory().getId() : null)
				.build();

		model.addAttribute("mode", "edit");
		model.addAttribute("courseId", id);
		model.addAttribute("courseForm", form);
		model.addAttribute("categories", getAllCategories());
		return "course-form";
	}

	@PostMapping("/courses/{id}")
	public String updateCourse(
			@PathVariable("id") Long id,
			@Valid @ModelAttribute("courseForm") CourseForm form,
			BindingResult bindingResult,
			Model model,
			RedirectAttributes redirectAttributes
	) {
		Optional<Course> courseOpt = courseRepository.findById(id);
		if (courseOpt.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy học phần");
			return "redirect:/home";
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("mode", "edit");
			model.addAttribute("courseId", id);
			model.addAttribute("categories", getAllCategories());
			return "course-form";
		}

		Course course = courseOpt.get();
		applyForm(course, form);
		courseRepository.save(course);

		redirectAttributes.addFlashAttribute("successMessage", "Cập nhật học phần thành công");
		return "redirect:/home";
	}

	@PostMapping("/courses/{id}/delete")
	public String deleteCourse(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		if (!courseRepository.existsById(id)) {
			redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy học phần");
			return "redirect:/home";
		}

		courseRepository.deleteById(id);
		redirectAttributes.addFlashAttribute("successMessage", "Xóa học phần thành công");
		return "redirect:/home";
	}

	private void applyForm(Course course, CourseForm form) {
		course.setName(form.getName());
		course.setImage(form.getImage() != null && !form.getImage().isBlank() ? form.getImage().trim() : null);
		course.setCredits(form.getCredits());
		course.setLecturer(form.getLecturer() != null && !form.getLecturer().isBlank() ? form.getLecturer().trim() : null);

		if (form.getCategoryId() == null) {
			course.setCategory(null);
			return;
		}

		Category category = categoryRepository.findById(form.getCategoryId()).orElse(null);
		course.setCategory(category);
	}

	private List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
}
