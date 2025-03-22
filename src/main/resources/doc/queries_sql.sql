create database taskmodel_db_v1;
drop database taskmodel_db_v1;

desc tb_user;
desc tb_task;

select * from tb_user;
select * from tb_task;

insert into tb_user (name, email) values
("Adão", "adao@gmail.com"),
("Eva", "eva@gmail.com"),
("Noé", "noe@gmail.com"),
("Abraão", "abraao@gmail.com"),
("Moisés", "moises@gmail.com"),
("Davi", "davi@gmail.com"),
("Salomão", "salomao@gmail.com"),
("Isaías", "isaias@gmail.com"),
("Daniel", "daniel@gmail.com"),
("João Batista", "joao.batista@gmail.com"),
("Zebedeu Abraão", "zebedeu@gmail.com"),
("Madalena Abraão", "madalena@gmail.com"),
("João Abraão", "joao@gmail.com");

insert into tb_task (date_task, description, status, title, user_id) values 
('2025/03/01', 'Build the ark', 'OPEN', 'Construction of Noahs Ark', 3),
('2025/03/02', 'Sacrifice Isaac', 'IN_PROGRESS', 'Sacrifice of Isaac', 4),
('2025/03/03', 'Free the people from Egypt', 'OPEN', 'Exodus from Egypt', 5),
('2025/03/04', 'Unite the tribes of Israel', 'OPEN', 'United Kingdom of Israel', 6),
('2025/03/05', 'Build the Temple of Jerusalem', 'OPEN', 'Construction of the Temple', 7),
('2025/03/06', 'Prophesy about the future of Israel', 'IN_PROGRESS', 'Prophecies of Isaiah', 8),
('2025/03/07', 'Interpret the kings dreams', 'OPEN', 'Interpretation of Dreams', 9),
('2025/03/08', 'Baptize people in the Jordan River', 'OPEN', 'Baptism in the Jordan River', 10),
('2025/03/09', 'Rule over the people of Israel', 'OPEN', 'Reign of David', 6),
('2025/03/10', 'Speak about the coming of the Messiah', 'CANCELLED', 'Prophecies about the Messiah', 8);