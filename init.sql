CREATE DATABASE spring;

-- Set the search path to 'spring' to ensure tables are created in the correct schema.
\c spring;

CREATE TABLE IF NOT EXISTS client (
    id SERIAL PRIMARY KEY,
    username VARCHAR(45) NOT NULL,
    password TEXT NOT NULL,
    algorithm VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS authority (
    id SERIAL PRIMARY KEY,
    name VARCHAR(45) NOT NULL,
    client INT NOT NULL
);


CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY,                                                                                                  name VARCHAR(45) NOT NULL,
    price VARCHAR(45) NOT NULL,
    currency VARCHAR(45) NOT NULL
);


INSERT INTO client (username, password, algorithm) VALUES
('john_doe', '$2a$12$ismFnCXj7izGOpgoMNs7wO1FmvT2SG6qd.yZXn2L4.Frz42WdlZJ.', 'BCRYPT');

INSERT INTO authority (name, client) VALUES
        ('ROLE_USER', '1'),
        ('ROLE_ADMIN', '1');

INSERT INTO product (name, price, currency) VALUES ('Laptop', '1200', 'USD');

