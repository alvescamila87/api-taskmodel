create database taskmodel_db_v1;
drop database taskmodel_db_v1;

desc tb_user;
desc tb_task;

select * from tb_user;
select * from tb_task;

insert into tb_user (name, email) values
("Zebedeu Abraão", "zebedeu@gmail.com"),
("Madalena Abraão", "madalena@gmail.com"),
("João Abraão", "joao@gmail.com");

insert into tb_task (date_task, description, status, title, user_id) values 
('2025/03/19', 'Description 1', 'OPEN', 'Task 1', 1),
('2025/03/16', 'Description 2', 'IN_PROGRESS', 'Task 2', 2),
('2025/03/12', 'Description 3', 'CANCELLED', 'Task 2', 3),
('2025/03/10', 'Description 1', 'OPEN', 'Task 13', 1),
('2025/03/13', 'Description 2', 'OPEN', 'Task 29', 2),
('2025/03/09', 'Description 3', 'OPEN', 'Task 21', 3);