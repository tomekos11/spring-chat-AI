CREATE TABLE conversations (
    id MEDIUMINT NOT NULL AUTO_INCREMENT,
    user_id MEDIUMINT NOT NULL,
    begin_date timestamp,

    FOREIGN KEY (user_id) REFERENCES users(id),
    PRIMARY KEY (id)
);

INSERT INTO conversations (user_id, begin_date) VALUES
    (1, NOW())