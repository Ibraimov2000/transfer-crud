

INSERT INTO roles(name)
VALUES ('ADMIN'),
       ('USER');


INSERT INTO users(username, password)
VALUES ('admin','admin'),
       ('user','$2a$12$KLW9JD75wEq6TH5viiPHjOtlwqkTRl289NJskPiG2b//WG/bZpmBi');


INSERT INTO account(name, surname, number)
VALUES ('Мирсеит','Ибраимов', 0776750817),
       ('Эрлан','Абдижамилов', 0776750817),
       ('Эрназар','Сыдыков', 0776750817),
       ('Мирлан','Ибраимов', 0776750817);

INSERT INTO currency(name)
VALUES ('сом'),
       ('$'),
       ('рубль');

INSERT INTO cash(balance)
VALUES (10000),
       (100000),
       (150000),
       (200000);

INSERT INTO users_roles(user_id, role_id)
VALUES (1, 1),
       (2, 2);