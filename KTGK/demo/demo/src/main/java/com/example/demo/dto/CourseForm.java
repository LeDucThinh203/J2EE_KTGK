package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseForm {
	@NotBlank(message = "Vui lòng nhập tên học phần")
	@Size(max = 200, message = "Tên học phần tối đa 200 ký tự")
	private String name;

	@Size(max = 500, message = "Image URL tối đa 500 ký tự")
	private String image;

	@NotNull(message = "Vui lòng nhập số tín chỉ")
	@Min(value = 1, message = "Số tín chỉ phải >= 1")
	@Max(value = 20, message = "Số tín chỉ phải <= 20")
	private Integer credits;

	@Size(max = 150, message = "Tên giảng viên tối đa 150 ký tự")
	private String lecturer;

	private Long categoryId;
}
