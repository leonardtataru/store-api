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
    role_id  BIGINT,
    CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES role (id)
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
    price DOUBLE,
    deleted   BOOLEAN
);

CREATE TABLE step_order
(
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    action VARCHAR(255)
);

CREATE TABLE journal
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT,
    order_id   BIGINT,
    CONSTRAINT fk_order_journal FOREIGN KEY (order_id) REFERENCES step_order (id)
);

INSERT INTO step_order (action)
values ('CREATE');
INSERT INTO step_order (action)
values ('CHANGE');
INSERT INTO step_order (action)
values ('DELETE');