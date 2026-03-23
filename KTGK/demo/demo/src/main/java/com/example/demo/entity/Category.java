package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
		name = "category",
		uniqueConstraints = {
				@UniqueConstraint(name = "uk_category_name", columnNames = "name")
		}
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;
}
