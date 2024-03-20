create table if not exists students (id bigserial primary key, name varchar(255), score int);

insert into students (name, score)
values
('Bob', 30),
('Jack', 80),
('Jo', 100),
('Jenifer', 11),
('Max', 34),
('Ilia', 84),
('Anton', 30),
('Nik', 85),
('Arnold', 99),
('Jan', 80),
('Alex', 100),
('Billi', 2),
('John', 50);