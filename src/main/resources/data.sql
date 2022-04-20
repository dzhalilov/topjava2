INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name, address, telephone)
VALUES ('Metropol', 'Volgograd', '123-456'),
       ('Пельменная', 'Москва', '+7-999-888-77-66'),
       ('Big fish', 'пр.Ленина, 1', '(555) 555-55-55');

INSERT INTO DISH (restaurant_id, dish_date, name, price)
VALUES (1, '2022-04-19', 'Суп old', 500),
       (1, '2022-04-19', 'Компот old', 30),
       (1, '2022-04-19', 'Гречка old', 60),
       (1, '2022-04-19', 'Котлета old', 150),
       (2, '2022-04-19', 'Пельмени классические old', 200),
       (2, '2022-04-19', 'Пельмени для детей old', 100),
       (2, '2022-04-19', 'Пельмени ассорти old', 300),
       (3, '2022-04-19', 'Уха осетровая old', 1800),
       (3, '2022-04-19', 'Стейк из сёмги old', 1000),
       (3, '2022-04-19', 'Рыба к пиву old', 750),
       (1, CURRENT_TIMESTAMP(), 'Суп1', 500),
       (1, CURRENT_TIMESTAMP(), 'Компот', 30),
       (1, CURRENT_TIMESTAMP(), 'Гречка', 60),
       (1, CURRENT_TIMESTAMP(), 'Котлета', 150),
       (2, CURRENT_TIMESTAMP(), 'Пельмени классические', 200),
       (2, CURRENT_TIMESTAMP(), 'Пельмени для детей', 100),
       (2, CURRENT_TIMESTAMP(), 'Пельмени ассорти', 300),
       (3, CURRENT_TIMESTAMP(), 'Уха осетровая', 1800),
       (3, CURRENT_TIMESTAMP(), 'Стейк из сёмги', 1000),
       (3, CURRENT_TIMESTAMP(), 'Рыба к пиву', 750);

INSERT INTO VOTE (vote_date, restaurant_id, user_id)
VALUES ('2022-04-04', 1, 1),
       ('2022-04-04', 3, 2),
       ('2022-04-03', 2, 1),
       ('2022-04-03', 2, 2),
       ('2022-04-02', 1, 1),
       ('2022-04-02', 1, 2);