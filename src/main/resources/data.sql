INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

# INSERT INTO RESTAURANTS (name, menu_id)
#     VALUE ('Metropol', 1),
#           ('Пельменная', 2),
#           ('Big fish', 3);
#
# INSERT INTO MENUS (name, meal_id)
