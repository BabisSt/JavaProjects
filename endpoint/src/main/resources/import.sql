-- Insert Authors First
INSERT INTO author (id, first_name, last_name, about_section) VALUES (1, 'George', 'Orwell', 'British writer');
INSERT INTO author (id, first_name, last_name, about_section) VALUES (2, 'Aldous', 'Huxley', 'English writer and philosopher');
INSERT INTO author (id, first_name, last_name, about_section) VALUES (3, 'Ray', 'Bradbury', 'American author known for Fahrenheit 451');

-- Insert Books with FK references to author.id
INSERT INTO book (isdn, title, content, author_id, release_date) VALUES ('9783161484100', '1984', 'Dystopian novel', 1, '1949-06-08');
INSERT INTO book (isdn, title, content, author_id, release_date) VALUES ('0306406152', 'Brave New World', 'Sci-fi novel', 2, '1932-01-01');
INSERT INTO book (isdn, title, content, author_id, release_date) VALUES ('9780131103627', 'Fahrenheit 451', 'Dystopian novel', 3, '1953-10-19');
