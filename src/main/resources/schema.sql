CREATE TABLE role
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255)
);

CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    role     BIGINT,
    CONSTRAINT fk_user_role FOREIGN KEY (role) REFERENCES role (id)
);

INSERT INTO role (description)
VALUES ('ADMIN');
INSERT INTO role (description)
VALUES ('USER');

CREATE TABLE product
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255),
    description VARCHAR(255),
    price DOUBLE
);