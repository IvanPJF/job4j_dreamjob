CREATE TABLE IF NOT EXISTS countries
(
    id   SERIAL,
    name TEXT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cities
(
    id           SERIAL,
    name         TEXT    NOT NULL,
    countries_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (countries_id) REFERENCES countries (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id          SERIAL,
    name        TEXT        NOT NULL,
    login       TEXT UNIQUE NOT NULL,
    email       TEXT        NOT NULL,
    create_date TIMESTAMP DEFAULT current_timestamp,
    password    TEXT        NOT NULL,
    role        TEXT        NOT NULL,
    cities_id   INTEGER     NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (cities_id) REFERENCES cities (id)
);

INSERT INTO countries (name)
VALUES ('Russia'),
       ('Germany'),
       ('France');

INSERT INTO cities (name, countries_id)
VALUES ('Moscow', 1), ('Saint Petersburg', 1), ('Kazan', 1),
       ('Berlin', 2), ('Frankfurt', 2), ('Hamburg', 2),
       ('Paris', 3), ('Lyon', 3), ('Nice', 3);

INSERT INTO users (name, login, email, password, role, cities_id)
VALUES ('root', 'root', 'root@root.com', 'root', 'admin', 1),
       ('user', 'user', 'user@mail.ru', 'user', 'user', 4),
       ('ivan', 'pjf', 'pjf@mail.ru', 'pjf', 'admin', 7);