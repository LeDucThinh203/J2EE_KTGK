package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
		name = "student",
		uniqueConstraints = {
				@UniqueConstraint(name = "uk_student_username", columnNames = "username"),
				@UniqueConstraint(name = "uk_student_email", columnNames = "email")
		}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private Long studentId;

	@Column(name = "username", nullable = false, length = 50)
	private String username;

	@Column(name = "password", nullable = false, length = 255)
	private String password;

	@Column(name = "email", nullable = false, length = 255)
	private String email;

	@ManyToMany
	@JoinTable(
			name = "student_role",
			joinColumns = @JoinColumn(name = "student_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	@Builder.Default
	private Set<Role> roles = new HashSet<>();
}
