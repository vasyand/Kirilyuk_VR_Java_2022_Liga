insert into users values
(1,'admin', 'Vasiliy', 'Kirilyuk', 'Romanovich', '$2a$10$8uKRAbibZEebzR83y.vM2OOGodevFRzHe6GPa4.bPh373H9gwulUW', 'ADMIN'),
(2,'user1', 'Ivan', 'Ivanov', 'Ivanovich', '$2a$10$8uKRAbibZEebzR83y.vM2OOGodevFRzHe6GPa4.bPh373H9gwulUW', 'USER'),
(3,'user2', 'Vlad', 'Vladov', 'Vladislavovich', '$2a$10$8uKRAbibZEebzR83y.vM2OOGodevFRzHe6GPa4.bPh373H9gwulUW', 'USER'),
(4,'user3', 'Sasha', 'Aleksandrov', 'Aleksandrovich', '$2a$10$8uKRAbibZEebzR83y.vM2OOGodevFRzHe6GPa4.bPh373H9gwulUW', 'USER'),
(5,'user4', 'Petr', 'Petrov', 'Petrovich', '$2a$10$8uKRAbibZEebzR83y.vM2OOGodevFRzHe6GPa4.bPh373H9gwulUW', 'USER'),
(6,'user5', 'Denis', 'Denisov', 'Denisovich', '$2a$10$8uKRAbibZEebzR83y.vM2OOGodevFRzHe6GPa4.bPh373H9gwulUW', 'USER'),
(7,'user6', 'Nikita', 'Nikitov', 'Nikitovich', '$2a$10$8uKRAbibZEebzR83y.vM2OOGodevFRzHe6GPa4.bPh373H9gwulUW', 'USER');

insert into projects values
                         (1, 'сделать rest api для проекта', 'rest'),
                         (2, 'навесить spring security', 'security');

insert into tasks values
                      (1, '2022-08-04', 'написать мапперы для дто', 'CREATED', 'мапперы', 2, 1),
                      (2, '2022-08-03', 'написать контроллеры', 'CREATED', 'контроллеры', 4, 1),
                      (3, '2022-08-02', 'написать тесты', 'RUN', 'тесты', 5, 1),
                      (4, '2022-08-04', 'написать спецификации', 'COMPLETED', 'спецификации', 6, 1),
                      (5, '2022-08-05', 'написать валидацию дто', 'CREATED', 'валидация', 3, 1),
                      (6, '2022-08-06', 'запилить аутентификацию по лоигну и паролю', 'CREATED', 'from based auth', 7, 2),
                      (7, '2022-08-07', 'защитить эндпоинты', 'CREATED', 'эндпоинты', 2, 2),
                      (8, '2022-08-08', 'реализовать userDetailsService', 'RUN', 'userDetailsService', 1, 2),
                      (9, '2022-08-09', 'token auth', 'CREATED', 'token', 3, 2),
                      (10, '2022-08-10', 'refresh token', 'COMPLETED', 'refresh token', 4, 2);

insert into comments values
                         (1, 'случаный коммент', 3),
                         (2, 'случаный коммент2', 2),
                         (3, 'случаный коммент3', 1),
                         (4, 'случаный коммент4', 4),
                         (5, 'случаный коммент5', 5);

insert into users_projects values
                        (4,1),
                        (1,2);