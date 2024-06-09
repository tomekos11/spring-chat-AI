CREATE TABLE shares (
        id MEDIUMINT NOT NULL AUTO_INCREMENT,
        slug VARCHAR(8),
        show_name BOOLEAN,
        conv_id MEDIUMINT NOT NULL,
        date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        expire_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

        FOREIGN KEY (conv_id) REFERENCES conversations(id),
        PRIMARY KEY (id)
);

INSERT INTO shares (slug, show_name, conv_id, date, expire_date) VALUES
    ('test', 1, 1, NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY))