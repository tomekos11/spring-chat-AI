CREATE TABLE conversations (
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    user_id MEDIUMINT NOT NULL,
    name VARCHAR(50),
    begin_date timestamp,

    FOREIGN KEY (user_id) REFERENCES users(id),
    PRIMARY KEY (id)
);

INSERT INTO conversations (user_id, name, begin_date) VALUES
    (1, 'test nazwy konwersacji', NOW())