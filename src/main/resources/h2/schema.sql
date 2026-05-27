CREATE TABLE role (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      description VARCHAR(255)
);

INSERT INTO role (description) VALUES ('ADMIN');
INSERT INTO role (description) VALUES ('USER');

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255),
                       password VARCHAR(255),
                       role_id BIGINT,
                       CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES role(id)
);