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

INSERT INTO DISH (name, price, restaurant_id)
VALUES ('Суп1', 500, 1),
       ('Компот', 30, 1),
       ('Гречка', 60, 1),
       ('Котлета', 150, 1),
       ('Пельмени классические', 200, 2),
       ('Пельмени для детей', 100, 2),
       ('Пельмени ассорти', 300, 2),
       ('Уха осетровая', 1800, 3),
       ('Стейк из сёмги', 1000, 3),
       ('Рыба к пиву', 750, 3);

INSERT INTO VOTE (vote_date, restaurant_id, user_id)
VALUES ('2022-04-04', 1, 1),
       ('2022-04-04', 3, 2),
       ('2022-04-03', 2, 1),
       ('2022-04-03', 2, 2),
       ('2022-04-02', 1, 1),
       ('2022-04-02', 1, 2);