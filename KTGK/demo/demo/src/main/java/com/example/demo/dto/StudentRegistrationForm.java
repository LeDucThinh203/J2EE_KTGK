package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRegistrationForm {

	@NotBlank(message = "Vui lòng nhập username")
	@Size(max = 50, message = "Username tối đa 50 ký tự")
	private String username;

	@NotBlank(message = "Vui lòng nhập password")
	@Size(min = 6, max = 255, message = "Password phải từ 6 đến 255 ký tự")
	private String password;

	@NotBlank(message = "Vui lòng nhập email")
	@Email(message = "Email không hợp lệ")
	@Size(max = 255, message = "Email tối đa 255 ký tự")
	private String email;
}