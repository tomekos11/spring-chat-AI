CREATE TABLE messages (
        id MEDIUMINT NOT NULL AUTO_INCREMENT,
        role VARCHAR(10),
        content VARCHAR(1000),
        conv_id MEDIUMINT NOT NULL,
        date timestamp,

        FOREIGN KEY (conv_id) REFERENCES conversations(id),
        PRIMARY KEY (id)
);

INSERT INTO messages (role, content, conv_id, date) VALUES
    ('user', 'elo elo', 1, NOW())