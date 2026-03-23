package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
		name = "role",
		uniqueConstraints = {
				@UniqueConstraint(name = "uk_role_name", columnNames = "name")
		}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long roleId;

	@Column(name = "name", nullable = false, length = 20)
	private String name;
}
