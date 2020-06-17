DO
$$
    BEGIN
        IF NOT EXISTS(SELECT 1 FROM pg_type WHERE typname = 'role_type') THEN
            create type role_type AS ENUM ('ADMIN', 'USER');
        END IF;
    END
$$;

CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    name     VARCHAR(30),
    surname  VARCHAR(30),
    role     role_type   NOT NULL DEFAULT 'USER',
    block    BOOLEAN
);

CREATE TABLE IF NOT EXISTS services
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(30) NOT NULL,
    description TEXT,
    price       FLOAT
);

CREATE TABLE IF NOT EXISTS bills
(
    id      SERIAL PRIMARY KEY,
    date    DATE    NOT NULL,
    price   FLOAT   NOT NULL,
    paid    BOOLEAN NOT NULL DEFAULT false,
    user_id INT REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS user_service
(
    user_id    INT REFERENCES users (id)    NOT NULL,
    service_id INT REFERENCES services (id) NOT NULL,
    selected   BOOLEAN                      NOT NULL DEFAULT true
);

insert into users (email, password, name, surname, role)
Select 'admin@mail.com', 'admin', 'admin', 'admin', 'ADMIN'
Where not exists(select * from users where email = 'admin@mail.com' and role = 'ADMIN');
