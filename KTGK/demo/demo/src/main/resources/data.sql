-- Seed initial roles
INSERT IGNORE INTO role (name) VALUES ('ADMIN');
INSERT IGNORE INTO role (name) VALUES ('STUDENT');

-- Seed sample categories
INSERT IGNORE INTO category (id, name) VALUES (1, 'CNTT');
INSERT IGNORE INTO category (id, name) VALUES (2, 'Kinh tế');

-- Seed sample courses (10 items -> pagination test: 5 per page)
INSERT IGNORE INTO course (id, name, image, credits, lecturer, category_id) VALUES
	(1, 'Lập trình Java', 'https://placehold.co/120x80?text=Java', 3, 'GV A', 1),
	(2, 'Cơ sở dữ liệu', 'https://placehold.co/120x80?text=DB', 3, 'GV B', 1),
	(3, 'Mạng máy tính', 'https://placehold.co/120x80?text=Network', 3, 'GV C', 1),
	(4, 'Hệ điều hành', 'https://placehold.co/120x80?text=OS', 3, 'GV D', 1),
	(5, 'Lập trình Web (J2EE)', 'https://placehold.co/120x80?text=Web', 3, 'GV E', 1),
	(6, 'Kinh tế vi mô', 'https://placehold.co/120x80?text=Micro', 2, 'GV F', 2),
	(7, 'Kinh tế vĩ mô', 'https://placehold.co/120x80?text=Macro', 2, 'GV G', 2),
	(8, 'Marketing căn bản', 'https://placehold.co/120x80?text=Marketing', 2, 'GV H', 2),
	(9, 'Phân tích dữ liệu', 'https://placehold.co/120x80?text=Data', 3, 'GV I', 1),
	(10, 'An toàn thông tin', 'https://placehold.co/120x80?text=Security', 3, 'GV K', 1);
