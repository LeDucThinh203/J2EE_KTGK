package com.example.demo.config;

import com.example.demo.entity.Role;
import com.example.demo.entity.Student;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class AdminBootstrap implements CommandLineRunner {

	private final StudentRepository studentRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) {
		if (studentRepository.findByUsername("admin").isPresent()) {
			return;
		}

		Role adminRole = roleRepository.findByName("ADMIN")
				.orElseGet(() -> roleRepository.save(Role.builder().name("ADMIN").build()));

		Student admin = Student.builder()
				.username("admin")
				.password(passwordEncoder.encode("admin123"))
				.email("admin@course.local")
				.roles(new HashSet<>(java.util.Set.of(adminRole)))
				.build();

		studentRepository.save(admin);
	}
}