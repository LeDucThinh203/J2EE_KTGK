package com.example.demo.repository;

import com.example.demo.entity.Enrollment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	boolean existsByStudent_UsernameAndCourse_Id(String username, Long courseId);

	@Query("select e.course.id from Enrollment e where e.student.username = :username")
	List<Long> findCourseIdsByStudentUsername(@Param("username") String username);

	@Query("""
		select e from Enrollment e
		join fetch e.course c
		left join fetch c.category
		where e.student.username = :username
		order by e.enrollDate desc, e.id desc
	""")
	List<Enrollment> findDetailedByStudentUsername(@Param("username") String username);
}
