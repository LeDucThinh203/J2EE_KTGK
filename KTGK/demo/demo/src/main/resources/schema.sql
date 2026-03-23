-- Schema for Course Registration Application (MySQL)

CREATE TABLE IF NOT EXISTS role (
  role_id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(20) NOT NULL,
  CONSTRAINT pk_role PRIMARY KEY (role_id),
  CONSTRAINT uk_role_name UNIQUE (name)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS student (
  student_id BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  CONSTRAINT pk_student PRIMARY KEY (student_id),
  CONSTRAINT uk_student_username UNIQUE (username),
  CONSTRAINT uk_student_email UNIQUE (email)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS student_role (
  student_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  CONSTRAINT pk_student_role PRIMARY KEY (student_id, role_id),
  CONSTRAINT fk_student_role_student FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE CASCADE,
  CONSTRAINT fk_student_role_role FOREIGN KEY (role_id) REFERENCES role(role_id) ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS category (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  CONSTRAINT pk_category PRIMARY KEY (id),
  CONSTRAINT uk_category_name UNIQUE (name)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS course (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(200) NOT NULL,
  image VARCHAR(500) NULL,
  credits INT NOT NULL,
  lecturer VARCHAR(150) NULL,
  category_id BIGINT NULL,
  CONSTRAINT pk_course PRIMARY KEY (id),
  CONSTRAINT fk_course_category FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS enrollment (
  id BIGINT NOT NULL AUTO_INCREMENT,
  student_id BIGINT NOT NULL,
  course_id BIGINT NOT NULL,
  enroll_date DATE NOT NULL,
  CONSTRAINT pk_enrollment PRIMARY KEY (id),
  CONSTRAINT uk_enrollment_student_course UNIQUE (student_id, course_id),
  CONSTRAINT fk_enrollment_student FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE CASCADE,
  CONSTRAINT fk_enrollment_course FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
) ENGINE=InnoDB;
