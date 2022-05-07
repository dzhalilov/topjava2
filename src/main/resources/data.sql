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
VALUES (1, '2022-04-19', 'Суп old', 50000),
       (1, '2022-04-19', 'Компот old', 3000),
       (1, '2022-04-19', 'Гречка old', 6000),
       (1, '2022-04-19', 'Котлета old', 15000),
       (2, '2022-04-19', 'Пельмени классические old', 20000),
       (2, '2022-04-19', 'Пельмени для детей old', 10000),
       (2, '2022-04-19', 'Пельмени ассорти old', 30000),
       (3, '2022-04-19', 'Уха осетровая old', 180000),
       (3, '2022-04-19', 'Стейк из сёмги old', 100000),
       (3, '2022-04-19', 'Рыба к пиву old', 75000),
       (1, CURRENT_DATE, 'Суп1', 50000),
       (1, CURRENT_DATE, 'Компот', 3000),
       (1, CURRENT_DATE, 'Гречка', 6000),
       (1, CURRENT_DATE, 'Котлета', 15000),
       (3, CURRENT_DATE, 'Уха осетровая', 180000),
       (3, CURRENT_DATE, 'Стейк из сёмги', 100000),
       (3, CURRENT_DATE, 'Рыба к пиву', 75000);

INSERT INTO VOTE (vote_date, restaurant_id, user_id)
VALUES ('2022-04-04', 1, 1),
       ('2022-04-04', 3, 2),
       ('2022-04-03', 2, 1),
       ('2022-04-03', 2, 2),
       ('2022-04-02', 1, 1),
       ('2022-04-02', 1, 2),
       (CURRENT_DATE, 3, 2);