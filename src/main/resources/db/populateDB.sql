DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, dateTime, description, calories) VALUES
  (100000, to_timestamp('08.04.2017 07:00:00', 'DD.MM.YYYY HH24:MI:SS'), 'Завтрак', 500),
  (100000, to_timestamp('08.04.2017 13:00:00', 'DD.MM.YYYY HH24:MI:SS'), 'Обед', 1000),
  (100000, to_timestamp('08.04.2017 18:00:00', 'DD.MM.YYYY HH24:MI:SS'), 'Ужин', 500),
  (100001, to_timestamp('09.04.2017 07:00:00', 'DD.MM.YYYY HH24:MI:SS'), 'Завтрак', 500),
  (100001, to_timestamp('09.04.2017 13:00:00', 'DD.MM.YYYY HH24:MI:SS'), 'Обед', 510),
  (100001, to_timestamp('09.04.2017 18:00:00', 'DD.MM.YYYY HH24:MI:SS'), 'Ужин', 1000);