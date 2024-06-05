CREATE TABLE users (
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(30),
    password VARCHAR(1024),
    enabled BOOLEAN,
    role VARCHAR(50),
    name VARCHAR(50),
    surname VARCHAR(50),
    email VARCHAR(55),
    phone VARCHAR(55),
    PRIMARY KEY (id)
);

INSERT INTO users (username, password, enabled, role, name, surname, email, phone) VALUES
   ('admin', 'password', 1, 'ADMIN', 'Tomasz', 'Słapiński', 'tom.slapinski@gmail.com', '+48 123 456 789'),
   ('tom', '123', 1, 'USER', 'Tom', 'S', 'tomek_slapinski@op.pl', '+48 696 696 696');

